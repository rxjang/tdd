# 화폐 예제
1부에서는 테스트 주도 개발의 리듬을 알아보도록 한다. 리듬은 다음과 같이 요약할 수 있다.
1. 재빨리 테스트를 하나 추가한다.
2. 모든 테스트를 샐행하고 새로 추가한 것이 실패하는지 확인한다.
3. 코드를 조금 바꾼다.
4. 모든 테스트를 실행하고 전부 성공하는지 확인한다.
5. 리팩토링을 통해 중복을 제거한다. 
---
## 1장. 다중 통화를 자원하는 Money객체
> 요구사항
> * 통화가 다른 두 금액을 더해서 주어진 환율에 맞게 변한 금액을 결과로 얻을 수 있어야 한다. 
> * 어떤 금액(주가)을 어떤 수(주식의 수)에 곱한 금액을 결과로 얻을 수 있어야 한다.

테스트를 먼저 만들어 보자.
```
public void testMultiplication() {
    Dollar five = new Dollar(5);
    five.times(2);
    assertEquals(10, five.amount);
}
```
위 코드는 컴파일 조차 되지 않는다. 다음의 총 네개의 컴파일 에러가 발생한다. 
* Dollar 클래스가 없음
* 생성자가 없음
* times(int) 메서드 없음
* amount 필드 없음

우선 컴파일이 되게하기 위해, 다음과 같이 코드를 작성헀다.

``` java
public class Dollar {

    int amount;

    public Dollar(int amount) {
    
    }

    void times(int multiplier) {
    
    }
}
```
컴파일 에러가 사라지고 테스트가 동작하지만, 테스트는 실패해 빨간 막대를 볼 수 있을 것이다. 이제 테스트를 통과시키도록 해보자.

``` java
    int amount = 10;
```
테스트가 성공해 초록색 막대를 보게 된다. 이제 위에서 말한 TDD 주기에 맞게 코드를 수정하자. 

``` java
public class Dollar {

    int amount;

    public Dollar(int amount) {
        this.amount = amount;
    }

    void times(int multiplier) {
        amount *= multiplier;
    }
}
```
이제 첫 번 째 테스트 완료 표시를 할 수 있게 됐다. 지금까지 한 작업은 다음과 같다. 
* 알고 있는 작업해야 할 테스트 목록을 만들었다.
* 오퍼레이션이 외부에서 어떻게 보이길 원하는지 말해주는 이야기를 코드로 표현했다. 
* Junit에 대한 상세한 사항들은 잠시 무시하기로 했다.
* 스텝 구현을 통해 테스트를 컴파일 했다.
* 끔찍한 죄악을 범하여 테스트를 통과시켰다.
* 돌아가는 코드에서 상수를 변수로 변경하여 점진적으로 일반화했다. 
* 새로운 할일들을 한번에 처리하는 대신 할일 목록에 추가하고 넘어갔다. 
---
## 2장. 타락한 객체
일반적인 TDD 주기는 다음과 같다. 
1. `테스트를 작성한다.` 올바른 답을 얻기 위해 필요한 이야기의 모든 요소를 포함해 인터페이스를 개발하라.
2. `실행 가능하게 만든다.` 빨리 초록 막대를 보는 것이 가장 중요하다. 깔끔하고 단순한 해법이 있지만 구현하는데 몇 분 정도 걸릴 것 같으면 적어 놓고 실행 되게 하라. 
3. `올바르게 만든다.` 시스템이 동작하므로 직전에 저질렀던 죄악을 수숩하자. 중복을 제거하자. 

즉, 작동하는 깔끔한 코드를 얻기 위해 나누어서 정복하는 것이다. `작동하는` 에 해당하는 부분을 먼저 해결하고 `깔꿈한 코드`부분을 먼저 해결하는 것이다. 

예제이서 Dollar에 대한 연산을 수행한 후에 해당 Dollar의 값이 바뀌는 것이 이상하다. 테스트코드를 다음과 같이 수정하자.

``` java
public void testMultiplication() {
    Dollar five = new Dollar(5);
    Dollar product = five.times(2);
    assertEquals(10, product.amount);
    product = five.times(3);
    assertEquals(15, product.amount);
}
```
``` java
Dollar times(int multiplier) {
    amount *= multiplier;
    return null;
}
```
테스트는 컴파일되지만 실패 한다. 테스트를 통과하기 위해 올바른 금액을 갖는 새 Dollar를 반환하자.

``` java
Dollar times(int multiplier) {
    return new Dollar(amount *= multiplier);
}
```

다음은 최대한 빨리 초록색을 보기 위해 취할 수 있는 전략이다.
* `가짜로 구현하기`: 상수를 반환하게 만들고 진짜 코드를 얻을 때까지 단계적으로 상수를 바꾸어간다.
* `명백한 구현 사용하기`: 실제 구현값을 입력한다. TDD를 사용할 때 위 두가지 방법을 번갈아 가며 사용해 구현을 더해나가게 될 것이다.  

---
## 3장. 모두를 위한 평등
### 값 객체 패턴
* 객체의 인스턴스 변수가 생성자를 통해서 설정된 후에는 결코 변하지 않는다.
* equals()를 구현해야 한다. 
* 해시 테이블의 키로 객체를 사용할 생각이라면 hashCode()를 같이 구현해야 한다.

---
## 4장. 프라이버시
앞 장에서 동치성 문제를 적용 했으므로, 코드를 수정해 보자.
``` java
public void testMultiplication() {
    Dollar five = new Dollar(5);
    assertEquals(new Dollar(10), five.times(2));
    assertEquals(new Dollar(15), five.times(3));
}
```
이로 인해 amount를 private으로 바꿀 수 있게 되었다.

``` java
private int amount;
```

하지만 위험한 상황을 만들었다. 동치성 테스트가 동치성에 대한 코드가 정확히 작동한다는 것을 검증해야 한다.

---

