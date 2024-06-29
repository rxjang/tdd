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
``` kotlin
fun testMultiplication() {
    val five = Dollar(5)
    five.times(2)
    assertThat(five.amount).isEqualTo(10)
}
```
위 코드는 컴파일 조차 되지 않는다. 다음의 총 네개의 컴파일 에러가 발생한다. 
* Dollar 클래스가 없음
* 생성자가 없음
* times(int) 메서드 없음
* amount 필드 없음

우선 컴파일이 되게하기 위해, 다음과 같이 코드를 작성헀다.

``` kotlin
class Dollar(
    var amount: Int,
) {

    fun times(multiplier: Int) {
      
    }
}
```
컴파일 에러가 사라지고 테스트가 동작하지만, 테스트는 실패해 빨간 막대를 볼 수 있을 것이다. 이제 테스트를 통과시키도록 해보자.

``` kotlin
fun times(multiplier: Int) {
    this.amount = 10
}
```
테스트가 성공해 초록색 막대를 보게 된다. 이제 위에서 말한 TDD 주기에 맞게 코드를 수정하자. 

``` kotlin
class Dollar(
    var amount: Int,
) {

    fun times(multiplier: Int) {
        this.amount *= multiplier
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
## 5장. 솔직히 말하자면
`$5 + 10CHF = $10(환율이 2:1일 경우)`  
이 테스트를 진행 해보자.
우선은 Dollar와 비슷하게 작동하는 Franc이라는 객체를 검증하기 위한 테스트 부터 만들어 보자.
``` java
public void testFrancMultiplication() {
    Franc five = new Franc(5);
    assertEquals(new Franc(10), five.times(2));
    assertEquals(new Franc(15), five.times(3));
}
```
우선 Fracn을 작동하기 위해 Dollar 객체를 복사해 Franc을 만들자. 중복은 다음에 제거하도록 하자.

---
## 6.돌아온 '모두를 위한 평등'
이제 중복을 제거해 보자. 가능한 방법중 한가 지로, 다른 클래스를 우리가 만든 클래스가 상속받도록 하자. 부모 클래스로 Money 객체를 만들자.
``` java
public class Money {
    proteced int amount;
}
```
하위 클래스에서도 amount를 볼 수 있도록 private에서 protected로 변경했다. 이제 equals() 코드를 위로 올려보자. 

``` java
public class Money {

    public boolean equals(Object object) {
        Money money = (Money) object;
        return amount == money.amount;
    }
}
```
Dollar객체와 Fran객체는 Money를 상속 받음으로써 중복을 줄일 수 있게 되었다.

``` java
public class Dollar extends Money {

    public Dollar(int amount) {
        this.amount = amount;
    }

    Dollar times(int multiplier) {
        return new Dollar(amount * multiplier);
    }

}
```
이제 중복은 해결했고, 테스트도 정상적으로 작동한다. 그런데, Franc과 Dollar를 비교하면 어떻게 될까? 

---
## 7장. 사과와 오렌지
Franc과 Dollar를 비교하는 테스트를 작성해보자.
``` java
public void testEquality() {
    assertTrue(new Dollar(5).equals(new Dollar(5)));
    assertFalse(new Dollar(5).equals(new Dollar(6)));
    assertTrue(new Franc(5).equals(new Franc(5)));
    assertFalse(new Franc(5).equals(new Franc(6)));
    assertFalse(new Franc(5).equals(new Dollar(5)));
}
```
Frac과 Dollar가 equal이어서 실패한다. 동치성 코드에서 두 객체의 클래스를 비교하게 하자. 오직 금액과 클래스가 서로 동일할 떄만 두 Money가 서로 같은 것이다. 
``` java
public class Money {

    public boolean equals(Object object) {
        Money money = (Money) object;
        return amount == money.amount
                && getClass().equals(money.getClass());
    }
}
```
모댈 코드에서 클래스를 이런 식으로 사용하는 것은 좀 지저분해 보인다. 하지만 현재는 통화(currency) 개념 같은 게 없으므로 잠시만 이대로 두겠다.

---
## 8장. 객체 만들기
Franc과 Dollar의 times() 구현 코드는 거의 똑같다. 양쪽 모두 Money를 반환하게 만들면 더 비슷하게 만들 수 있다.
``` java
Money times(int mutiplier) {
    return new Franc(amount * mutiplier);
}

Money times(int mutiplier) {
    return new Dollar(amount * mutiplier);
}
```

다음으론, Money의 두 하위 클래스는 그다지 많은 일을 하지 않아 보인다. 
하위 클래스에 대한 직접적인 참조가 적어진다면 하위 클래스를 제거하기 위해 한발 짝 더 다가섰다고 볼 수 있겠다. 
Money에 하위 클래스를 반환하는 팩토리 메서드를 도입 해 보자.

``` java
public void testMultiplication() {
    Money five = Money.dollar(5);
    assertEquals(Money.dollar(10), five.times(2));
    assertEquals(Money.dollar(15), five.times(3));
}
```
Money 객체를 아래와 같이 수정한다. times()를 정의할 준비가 안되었기 때문에 Money를 추상클래스로 변경한 후, Money.times()를 선언하자. 

**Money**
``` java
abstract class Money {

    ...

    static Dollar dollar(int amount) {
        return new Dollar(amount);
    }

    abstract Money times(int mutiplier);
}
```
모든 테스트가 실행되는 것을 확인 할 수 있다. Franc도 위와 같이 수정하자.
이렇게 수정함으로 인해 어떤 클라이언트 코드도 Dollar라는 하위 클래스가 있다는 사실을 알지 못한다. 
하위 클래스를 테스트에서 분리 (decoupling)함으로써 어떤 모델 코드에도 영향을 주지 않고 상속 구조를 마음대로 변경할 수 있게 됐다. 

---
## 9장. 우리가 사는 시간
통화 개념을 테스트 해보재
``` java
public void testCurrency() {
    assertEquals("USD", Money.dollar(1).currency());
    assertEquals("CHF", Money.franc(1).currency());
}
```
우선 Money에 currency() 메서드를 선언하자.

``` java
abstract String currency();
```
그 다음, 드 하위 클래스에서 이를 구현 하자.
``` java
// Franc
String currency() {
    return "CHF";
}
// Dollar
String currency() {
    return "USD";
}
```
두 클래스를 모두 포함할 수 있는 동일한 구현을 할 수 있을 것이다. 통화를 인스턴스 변수에 저장하고, 메서드에서는 그냥 반환하게 만들어 보자.
``` java
// Franc
private String currency;

Franc(int amount) {
    this.amount = amount;
    currency = "CHF";
}

String currency() {
    return currency;
}
```
이렇게 하면 두 currency()가 동일하므로 변수 선언과 currency() 구현울 달다 위로(Money) 올릴 수 있게 되었다. 

``` java
// Money
protected String currency;

String currency() {
    return currency;
}
```
문자열 "USD"와 "CHF"를 정적 팩토리 메서드로 옭긴다면 두 새엇ㅇ자가 동일해 질 것이고, 그렇다면 공통 구현을 만들 수 있다. 
``` java
Franc(int amount, String currency) {
    this.amount = amount;
    this.currency = "CHF";
}
```
생성자를 호출 하는 두 곳이 깨지게 된다. Franc 내의 times 같은 경우는 Mooney.franc()를 사용해 팩토리 메서도를 호출하도록 하자.
``` java
Money times(int multiplier) {
    return Money.franc(amount * multiplier);
}
```
이제 팩토리 메서드가 "CHF"를 전달 할 수 있게 되었고, 인자를 인스턴스 변수에 할당 할 수 있다.
``` java
static Franc franc(int amount) {
    return new Franc(amount, "CHF");
}
```
``` java
Franc(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
}
```
Dollar도 유사한 방ㅎ식으로 수정 했다. 두 생성자가 이제 동일해 졌으므로 구현을 상위 클래스로 옮기자.
``` java
public Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
}
```
``` java
Franc(int amount, String currency) {
    super(amount, currency);
}
```
times()를 상위 클래스로 올리고 하위 클래스들을 제거할 준비가 거의 다 됐다.

---
## 10장. 흥미로운 시간
Money를 나태기위한 단 하나의 클래스만을 갓도록 수정해보자.  
Franc과 Dollar 클래스의 times() 메소드를 동일하게 만들기 위한 명학한 방법이 없다. 팩토리 메서드를 인라인으로 수정하자.
``` java
Money times(int multiplier) {
    return new Franc(amount * multiplier, currency);
}
```
Dollar도 위와 같은 방식으로 변경하자. 그런데, 정말 Franc을 반환하는 것 일까? 우선 Money를 반환하도록 고쳐보자.
``` java
Money times(int multiplier) {
    return new Money(amount * multiplier, currency);
}
```
그러자 컴파일러가 Money를 콘크리트 클래스로 바꿔야 한다고 말한다. 아래와 같이 수정하자.
``` java
class Money {
    ,,,
    Money time(int amount) {
        return null;
    }
}
```
그리고 테스트를 돌리면 빨긴 막대가 뜬다. 메시지를 보기 위해 toString()을 정의하자.
``` java
 public String toString() {
    return amount + " " + currency ;
}
```
그 후 테스트를 돌리면 메시지를 명확히 확인할 수 있다.

`com.example.tdd.money.Franc@6d6d480c<10 CHF> but was: com.example.tdd.money.Money@e95595b<10 CHF>`  
클래스가 달라 에러가 발생한 것이다. 문제는 equals()구현에 있다. 
``` java
public boolean equals(Object object) {
    Money money = (Money) object;
    return amount == money.amount
            && getClass().equals(money.getClass());
}
```
여기서 검사해야 할 것은 클래스가 같은지 아니라 currency가 같은지 여부다. 이를 위한 테스트 코드를 작성하자.
``` java
public void testDifferentClassEquality() {
    assertTrue(new Money(10, "CHF").equals(new Franc(10, "CHF")));
}
```
equals() 코드는 클래스가 아니라 currency를 비교하도록 변경하자. 
``` java
public boolean equals(Object object) {
    Money money = (Money) object;
    return amount == money.amount
            && currency().equals(money.currency());
}
```
이제 모든 테스트가 동작한다. 두 구현이 동일해 졌으니, 상위클래스로 끌어올리자. 
``` java
Money times(int mutiplier) {
    return new Money(amount * mutiplier, currency);
}
```
곱하기도 구현했으니, 하위 클래스들을 제거할 수 있겠다.

---
## 11장. 모든 악의 근원
두 하위 클래스 Dollar와 Franc은 달랑 생성자 밖에 없다. 생성자 때문에 하위 클래스가 있을 필요는 없기 때문에 하위 클래스를 제거하자.
``` java
static Money dollar(int amount) {
    return new Money(amount, "USD");
}

static Money franc(int amount) {
    return new Money(amount, "CHF");
}
```
이제 Dollar에 대한 참조는 하나도 없으므로 Dollar는 지워버리자. 반면에 Franc은 테스트 코드에서 아직 참조한다. 
testDifferentClassEquality()를 지워도 될 정도로 다른 곳에서 테스트를 충분히 하고 있는지 testEquality()를 돌려보자.
``` java
public void testEquality() {
    assertTrue(Money.dollar(5).equals(Money.dollar(5)));
    assertFalse(Money.dollar(5).equals(Money.dollar(6)));
    assertFalse(Money.franc(5).equals(Money.dollar(5)));
}
```
테스트가 잘 돌아간다. 중복된 테스트 단언은 지우자. Franc과 함께 testDifferentClassEquality() 도 지워버리자.
testFrancMultiplication()을 지워도 시스템 동작에 대한 신뢰는 잃지 않을 것이다.

---
## 12장. 드디어, 더하기
간단한 더하기 테스트 코드를 작성해보자.
``` java
public void testSimpleAddition() {
    Money sum = Money.dollar(5).plus(Money.dollar(5));
    assertEquals(Money.dollar(10), sum);
}
```
plus는 다음과 같이 구현해 보자.
``` java
Money plus(Money added) {
    return new Money(amount + added.amount, currency);
}
```
설계상 가장 어려운 제약은 다중 통화 사용에 대한 내용을 시스템의 나머지 코드에 숨기고 싶다는 것이다. 객체를 사용해 이를 해결하자. 
Money와 비슷하게 동작하지만 사실은 두 Money의 합을 나타내는 객체를 만듦으로서 이를 해결 할 수 있을 것이다. 
이를 설명하기 위해 두 가지 메타포를 사용하자. 
1. Money의 합을 마치 지갑처럼 취급하는 것이다. 한 지갑에는 금액과 통화가 다른 여러 화폐들이 들어갈 수 있다.
2. '(2 +3) x 5'와 같은 수식이다. 이렇게 하면 Money를 수식의 가장 작은 단위로 볼 수 있다. 연산의 결과로 Expression들이 생기는데, 그 중 하나는 합(sum)이 될것이다. 연산이 완료되면, 환율을 이용해 결과 Expression을 단일 통화로 축약할 수 있다. 

이 메타포를 테스트에 적용해보자.  
``` java
public void testSimpleAddition() {
    Money sum = Money.dollar(5).plus(Money.dollar(5));
    Money reduced = bank.reduce(sum, "USD");
    assertEquals(Money.dollar(10), reduced);
}
```
컴파일하기 위해 Expression 인터페이스가 필요하다. 
``` java
public interface Expression {
}
```
Money.plus()는 Expression을 반환해야 한다. 
``` java
Expression plus(Money money) {
    return new Money(amount + added.amount, currency);
}
```
이건 Moneyrk Expression을 구현해야한다는 뜻이다. 
``` java
class Money implements Expression {
    ...
}
```
이제 reduce()스텁이 있는 Bank클래스가 필요하다. 
``` java
public class Bank {
    Money reduce(Expression source, String to) {
        return Money.dollar(10);
    }
}
```
다시 초록막대로 돌아왔고, 리펙토링 준비가 완료되었다. 

---
## 13장. 진짜로 만들기
우선, Money.plus()는 그냥 Money가 아닌 Expression(sum)을 반환해야 한다. 두 Money의 합은 Sum이어야 한다. 
``` java
public void testPlusReturnSum() {
    Money five = Money.dollar(5);
    Expression result = five.plus(five);
    Sum sum = (Sum) result;
    assertEquals(five, sum.augend);
    assertEquals(five, sum.addend);
}
```
위 테스트는 금방 수정될 것이다. 위 테스트는 수행하고자 하는 연산의 외부 행위가 아닌 내부 구현에 대해 너무 깊게 관여하고 있다. 
이 코드를 컴파일하기 위해선 augend와 addend필드를 가지는 Sum클래스가 팔요하다. 
``` java
public class Sum {
    Money augend;
    Money addend;
}
```
Money.plus()는 Sum이 아닌 Money를 반환하게 되어 있기 때문에, 코드는 ClassCastException을 발생시킨다.
``` java
Expression plus(Money addend) {
    return new Sum(this, addend);
}
```
Sum생성자도 필요하다. 그리고 Sum은 Expression의 일종이어야 한다. 
``` java
public class Sum implements Expression{

    public Sum(Money augend, Money addend) {
        this.addend = addend;
    }
    ```
}
```
이제 시시틈에 다시 컴파일되는 상태로 돌아왔다. 하지만 테스트는 여전히 실패하는데, Sum생성자에서 필드를 설정하지 않기 때문이다. 
``` java
public Sum(Money augend, Money addend) {
    this.augend = augend;
    this.addend = addend;
}
```
이제 Bank.reduce()는 Sum을 전달받는다. 만약 Sum이 가지고 있는 Money의 통화가 모두 동일하고, reduce를 통해 얻어내고자 하는 Money의 통화 역시 같다면, 결과는 Sum내에 있는 Money들의 amount를 합친 값을 갖는 Money객체여야 한다.
``` java
public void testReduceSum() {
    Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
    Bank bank = new Bank();
    Money result = bank.reduce(sum,  "USD");
    assertEquals(Money.dollar(7), result); 
}
```
Sum을 계산하면 결과는 Money가 되어야 하며, 그 Money의 양은 두 Money 양의 합이고, 통화는 우리가 축약하는 통화여야 한다.
### Bank
``` java
Money reduce(Expression source, String to) {
    Sum sum = (Sum) source;
    int amount = sum.augend.amount + sum.addend.amount;
    return new Money(amount, to);
}
```
이 코드는 다음 이유로 지저분하다.
* 형변환. 이 코드는 모든 Expression에 대해 작동해야한다.
* 공용 필드와 그 필드들에 대한 두 단계에 거친 래퍼런스

이를 고치기 위해 외부에서 접근 가능한 필드 몇개를 들어내기 위해 메서드 본문을 Sum으로 옮길 수 있다. 
### Bank
``` java
Money reduce(Expression source, String to) {
    Sum sum = (Sum) source;
    return sum.reduce(to);
}
```
### Sum
``` java
public Money reduce(String to) {
    int amount = augend.amount + addend.amount;
    return new Money(amount, to);
}
```
테스트를 구현해보자.
``` java
public void testReduceMoney() {
    Bank bank = new Bank();
    Money result = bank.reduce(Money.dollar(1),  "USD");
    assertEquals(Money.dollar(1), result);
}
```
``` java
Money reduce(Expression source, String to) {
    if (source instanceof Money) {
        return (Money) source;
    }
    Sum sum = (Sum) source;
    return sum.reduce(to);
}
```
지저분하지만 리팩토링을 할 수 있다. 클래스를 명시적으로 검사하는 코드가 있을 때에는 항상 다형성을 사용하도록 바꾸는 것이 좋다. 
### Bank
``` java
Money reduce(Expression source, String to) {
    if (source instanceof Money) {
        return (Money) source.reduce(to);
    }
    Sum sum = (Sum) source;
    return sum.reduce(to);
}
```
### Money
``` java
public Money reduce(String to) {
    return this;
}
```
### Expression
``` java
Money reduce(String to);
```
이제 지저분한 캐스틍과 클래스 검사 코드를 제거 할 수 있다. 
### Bank
``` java
Money reduce(Expression source, String to) {
    Sum sum = (Sum) source;
    return sum.reduce(to);
}
```
---
## 14장. 바꾸기
통화 변환을 수행하는 테스트 코트드를 작성해보자. 
``` java
public void testReduceMoneyDifferentCurrency() {
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);
    Money result = bank.reduce(Money.franc(2), "USD");
    assertEquals(Money.dollar(1), result);
}
```
프랑을 달러로 변환할 때 간단히 나누기 2를 하고, 코드를 작성하자. 
### Money
``` java
public Money reduce(String to) {
    int rate = (currency.equals("CHF") && to.equals("USD"))
            ? 2
            : 1;
    return new Money(amount / rate, to);
}
```
이 코드로 인해 Money가 환율에 대해 알게 돼 버렸다. 환울에 대한 일은 모두 Bank가 처리해야 한다. 
### Bank 
``` java
Money reduce(Expression source, String to) {
    return source.reduce(this, to);
}
```
### Expression
``` java
Money reduce(Bank bank, String to);
```
### Sum
``` java
public Money reduce(Bank bank, String to) {
    int amount = augend.amount + addend.amount;
    return new Money(amount, to);
}
```
### Money
``` java
 public Money reduce(Bank bank, String to) {
    int rate = (currency.equals("CHF") && to.equals("USD"))
            ? 2
            : 1;
    return new Money(amount / rate, to);
}
```
인터페이스에 선언된 메서드는 공용이어야 하므로 Money의 reduce()도 공용어이어 한다. 이제 환율을 Bank에서 계산할 수 있게 됐다.
### Bank
``` java
int rate(String from, String to) {
    return (from.equals("CHF") && to.equals("USD")) 
            ? 2 
            : 1;
}
```
### Money
``` java
public Money reduce(Bank bank, String to) {
    int rate = bank.rate(currency, to);
    return new Money(amount / rate, to);
}
```
2가 아직도 테스트와 코드 모두에 나온다. 이걸 없애기 위해 Bank에 환율표를 가지고 있다가 필요할 때 찾아볼 수 있게 해야한다. 이를 위한 객체를 따로 만들자.
``` java
private class Pair {
    private String from;
    private String to;

    Pair(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public boolean equals(Object object) {
        Pair pair = (Pair) object;
        return from.equals(pair.from) && to.equals(pair.to);
    }

    public int hashCode() {
        return 0;
    }
}
```
0은 최악의 해시 코드다. 나중에 많은 통화를 다루게 될 때 개선하자.  
환율을 저장할 곳과 설정할 코드가 필요하다. 또, 필요할 떄 환율을 얻어낼 수도 있어야 한다. 
``` java
class Bank {

    private Hashtable<Pair, Integer> rates = new Hashtable();

    ```
    
    int rate(String from, String to) {
        return rates.get(new Pair(from, to));
    }

    void addRate(String from, String to, int rate) {
        rates.put(new Pair(from ,to), rate);
    }
}
```
USD에서 USD로의 환율을 요청하면 그 값이 1이되어야 한다는 테스트를 추가해야 한다. 
``` java
public void testIdentityRate() {
    assertEquals(1, new Bank().rate("USD", "USD"));
}
```
이는 아래와 같이 해결 할 수 있다. 
### Bank
``` java
int rate(String from, String to) {
    if (from.equals(to)) return 1;
    return rates.get(new Pair(from, to));
}
```
---
## 15장. 서로 다른 통화 더하기
이제 $5 + 10CHF에 대한 테스트를 할 준비가 되었다. 
``` java
public void testMixedAddition() {
    Expression fiveBucks = Money.dollar(5);
    Expression tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);
    Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
    assertEquals(Money.dollar(10), result);
}
```
컴파일에러가 발생한다. 우선 컴파일되도록 수정해보자.
``` java
 public void testMixedAddition() {
    Money fiveBucks = Money.dollar(5);
    Money tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);
    Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
    assertEquals(Money.dollar(10), result);
}
```
테스가 실패한다. 10USD대신 15USD가 나왔다. Sum.reduce()가 인자를 축약하지 않았다. 인자들을 축약하면 테스트가 통과할 것이다.
### Sum
``` java
public Money reduce(Bank bank, String to) {
    int amount = augend.reduce(bank, to).amount 
            + addend.reduce(bank, to).amount;
    return new Money(amount, to);
}
```
테스트에 통과했다. Expression이어야하는 Money들을 조금씩 없애보자. 
### Sum
``` java
Expression augend;
Expression addend;

public Sum(Expression augend, Expression addend) {
    this.augend = augend;
    this.addend = addend;
}
```
plus()인자와 times()의 반환값도 Expression으로 취급 될 수 있다.
``` java
Expression plus(Expression addend) {
    return new Sum(this, addend);
}

Expression times(int mutiplier) {
    return new Money(amount * mutiplier, currency);
}
```
이제 테스트 케이스에 나오는 plus()의 인자도 바꿀수 있다. 
``` java
 public void testMixedAddition() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
        assertEquals(Money.dollar(10), result);
}
```
Expression에 plus()를 정의하자.
``` java
Expression plus(Expression addend);
```
이제 Money와 Sum에도 추가해야한다. 
### Money
``` java
public Expression plus(Expression addend) {
    return new Sum(this, addend);
}
```
### Sum
``` java
public Expression plus(Expression addend) {
    return null;
}
```
Sum 구현은 스텁으로 구현하고, 추후에 수정하자. 