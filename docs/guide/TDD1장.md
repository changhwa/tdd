### 자판기 프로그램으로 연습하는 TDD
TDD에 대한 잡설은 많은 곳에서 언급하는 만큼 제외하고,  
프로젝트 설정 방법도 많은 곳에서 나온 만큼 코딩과 과정에 집중하겠다.  
<br/>
일단 프로그래밍을 하기전에 기본적으로 해야하는건 요구사항정의 일 것이다.  
자판기에 관한 요구 사항부터 정리한다.

##### 요구사항

    1. 음료수를 구매 할 수 있다.
    2. 지폐는 1000,5000,10000 원짜리를 넣을 수 있다.
    3. 거스름돈은 최대 -> 최소 순으로 나온다. ( 예 천원을 넣고 300원짜리 음료수를 뽑으면 500원짜리 1개 (최대) -> 100원짜리 2개 (최소) )
    4. 동전을 넣을 수 있다.
    5. 음료수를 변경한다.
    6. 음료수의 재고를 확인한다.
    7. 동작 중 총 판매금액을 확인한다.
    8. 동작 중 남은 거스름돈을 확인한다.
    9. 현재 넣은 금액에서 가능한 음료수에 불이 들어온다.

요구 사항은 이정도로 정리가 가능 할 것이다.  
개발하면서 추가 될 수 도 있고 제거될 수 도 있다. 앞으로는 이것을 중점으로 진행하겠다.  
여기서 중요한건 동사 인데 이것을 다시한번 간략하게 정리해보면.. 이렇게 정리될 수 있다.

##### 행위정리

    1. 구매한다 (음료수를)
    2. 받는다 (지폐를)
    3. 받는다 (동전을)
    4. 계산한다 (음료수를)
    5. 돌려준다 (거스름돈을)
    6. 교체한다 (음료수를)
    7. 확인한다 (음료수의 재고를)
    8. 확인한다 (판매금액을)
    9. 확인한다 (남은 거스름돈을)
    10. 확인한다 (현재 금액에 맞는 음료수를)

원래는 조건도 정리해두는것이 맞으나 이렇게 하다보면 언제 개발을 시작할지 모른다.  
일단 빠르게 개발해보는것이 목적이니 만큼 조건은 차차 정리해나가기로 하자.

##### 프로그램의 시작
일단 언어는 Groovy , 테스트프레임워크는 Junit을 확장한 Spock을 사용할 것이고  
빌드도구로는 Gradle을 사용할 것이다.  
spock에 관련해 셋팅하는 방법은 아래 최범균님의 블로그를 참고한다.  
[Spock 2 환경 설정 하기(Eclipse 4.3 + Maven)](http://javacan.tistory.com/313)  
<br/>
build.gradle
```
apply plugin:'application'
apply plugin: 'groovy'
apply plugin: 'idea'
apply plugin: 'java'

//eclipse project일땐위에 idea를 주석하고 이걸 풀어주세요
//apply plugin: 'eclipse'

sourceCompatibility = '1.7'
targetCompatibility = '1.7'

group = 'com.narratage.vend'
version = '1.0.0'

mainClassName = "com.narratage.vend.App"

repositories {
    mavenCentral()
    mavenLocal()
    maven { url "http://oss.sonatype.org/content/repositories/snapshots/" }
}


dependencies {
    compile 'org.codehaus.groovy:groovy-all:2.1.9'
  	compile 'org.slf4j:slf4j-api:1.7.5'
    testCompile "org.spockframework:spock-core:1.0-groovy-2.0-SNAPSHOT"
    testCompile "org.hamcrest:hamcrest-core:1.3"
    testRuntime "cglib:cglib-nodep:2.2.2"

}

sourceSets {
    main {
        groovy {
            srcDirs = ['src/main/groovy']
        }
    }

    test {
        groovy {
            srcDirs = ['src/test/groovy']
        }
    }
}

[compileGroovy, compileTestGroovy]*.options*.encoding = 'UTF-8'

sourceSets.main.output.resourcesDir = sourceSets.main.output.classesDir
sourceSets.test.output.resourcesDir = sourceSets.test.output.classesDir

task initProject {
	project.sourceSets*.allSource.srcDirTrees.flatten().dir.each { dir ->
        dir.mkdirs()
    }
}
```

이정도로 됐으면 이제 테스트 코드를 빠르게 구성해보겠다.  
Intellij 사용자 분들은 테스트 클래스를 단축키로 만드는것에 익숙하리라 생각한다.  
나는 com.narratage.vend.machine 패키지에  
Machine 클래스를 생성하고 같은패키지에 테스트 패키지경로에 자동으로 생성했다.  
![](http://d.pr/i/KcXy+)
> 여기서 잠깐  
given // when // then 탬플릿은 이제 필수라고 할 정도로 테스트 코드를 작성함에 있어
상당히 중요합니다. 가독성을 높여줄 뿐만 아니라 시나리오에 맞게금 작성함으로써 좀 더 효율적인 테스트
코드를 작성 할 수 있습니다.

위의 코드를 실행해보면 진리의 초록막대기가 나온다.
![](http://d.pr/i/UUkc+)  

이제 실제코드를 구현해본다.  
자판기에서 콜라를 뽑아 보자  

![](http://d.pr/i/4Xry+)

위의 코드는 물품이름을 입력받아 리턴하도록 되어있다.  
이제 테스트 코드를 작성하겠다.  
(원래는 테스트 코드를 먼저 만드는게 맞다는 분들도 있는데.. 에러 나는거 보기가 싫어서 이런형태로 진행한다.)  

![](http://d.pr/i/4d49+)

테스트를 돌려보자.  

![](http://d.pr/i/Xq6n+)

성공했음을 알 수 있다.
  
그런데 우리는 여기서 한가지 더 고민을 해봐야한다.  
음료수 자판기는 대부분 여러개의 음료수를 갖고 있기 때문인데..  
해당 코드에서는 이러한 부분이 고려가 되지 않았다.  
이제 음료수를 여러개를 갖고 있도록 해보겠다.  
  
Machine.groovy
```java
	static List itemList = new ArrayList()

    static{
        itemList.add("콜라")
        itemList.add("사이다")
        itemList.add("환타")
        itemList.add("밀키스")
        itemList.add("박카스")
    }
    
    def buy(int itemIndex){
        itemList.get(--itemIndex)
    }
```
실코드는 이런 형태로 수정해보았다.  
itemList라는 변수를 만들어서 각 음료수들로 초기화 하였고  
이에 따라 buy 메소드에서는 번호로 선택해서 뽑을수 있도록 하였다.  
너무 한번에 호흡을 길게 가져간 면도 있지만 이 정도는 쉽게 생각할 수 있으므로 이렇게 하자.  
이제 테스트 코드를 수정하겠다.  
콜라 뿐만 아니라 환타도 뽑아봐야겠다.  
  
MachineTest.groovy
```java
	def "콜라를 구매한다"(){

        given: "콜라가 첫번째 버튼이라고 가정하고"
        int wantItem = 1

        when: "콜라 버튼을 눌렀을때"
        String buyItem = machine.buy(wantItem)

        then: "자판기에서 콜라가 나온다"
        buyItem == "콜라"

    }

    def "환타를 구매한다"(){

        given: "환타가 세번째 버튼이라고 가정하고"
        int wantItem = 3

        when: "환타 버튼을 눌렀을때"
        String buyItem = machine.buy(wantItem)

        then: "자판기에서 환타가 나온다"
        buyItem == "환타"

    }
```  
음 이상이 없어 보인다.  
한번 테스트를 돌려보자.  
  
![](http://d.pr/i/W5LQ+)  
  
정상적으로 테스트가 성공했음을 알 수 있다.
  
그런데 곰곰히 생각해보니 *(사실 실제로는 말이 안되지만)* 잘못된 버튼을 누를수도 있다는 생각이 들었다.  
일단 잘못된 버튼을 누를 테스트 케이스를 하나 추가했다.  
  
MachineTest.groovy  
```java
	def "잘못된 버튼을 누른다"(){

        given: "잘못된 버튼 (여기선 리스트의 개수를 초과하는 6) 이 있다고 가정하고"
        int wantItem = 6

        when: "잘못된 버튼을 누를때"
        String buyItem = machine.buy(wantItem)

        then: "없음  이라는 멘트가 뜬다"
        buyItem == "없음"
    }
```   
테스트를 돌려보자.
  
![](http://d.pr/i/kz3F+)
  
역시 에러가 발생한다.  
리스트 개수를 초과한 수를 입력한 것이니 당연할 것이다.  
이제 고민은 해당 예외가 나오면 성공으로 할것이냐 아니면 소스를 수정할것이냐 이다.  
하지만 잘못된 버튼을 누르면 없음 이라는 멋진 멘트가 나왔으면 좋겠다.  
그러면 소스를 한번 수정하는게 낫겠다.  
  
Machine.groovy
```java
	def buy(int itemIndex){
        if(itemList.size() <= itemIndex)
            return "없음"
        itemList.get(--itemIndex)
    }
```  
쉽게 생각해서 입력받은 버튼 값이 리스트의 사이즈보다 크거나 같으면 리스트의 수를 초과하니까  
이럴 경우 `없음` 이라는 멘트를 던져주었다.  
테스트를 실행해보겠다.  
  
![](http://d.pr/i/mm9x+)  
  
정상적으로 테스트가 성공했다.  
이것으로 기본적인 버튼을 눌러서 음료수를 구매하는 부분은 완료하였다.  
다시 행위를 정리해둔 것을 보자.  
    1. 구매한다 (음료수를)
    2. 받는다 (지폐를)
    3. 받는다 (동전을)
    4. 계산한다 (음료수를)
    5. 돌려준다 (거스름돈을)
    6. 교체한다 (음료수를)
    7. 확인한다 (음료수의 재고를)
    8. 확인한다 (판매금액을)
    9. 확인한다 (남은 거스름돈을)
    10. 확인한다 (현재 금액에 맞는 음료수를)
    
이것으로 1번이 완료 되었다.  
다음번 연재에서는 2,3,4번을 구현할 예정이다.  
<br/>
<br/>
<br/>
해당 연재는 TDD 초보자분들에게 조금이나마 이런형태로 진행해보면 어떨까를 제의 하는 연재입니다.  
또한 연재를 하면서 제가 그루비의 문법을 다시 한번 공부하는 계기로 삼으려고도 합니다.  
여러가지 리팩토링 상황에 대해서도 진행해보고 재밌게 해나갈 생각입니다.  
언제든지 의견은 changhwaoh.co@gmail.com 으로 주시면 감사하겠습니다.