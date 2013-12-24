package com.narratage.vend.machine

import spock.lang.Specification

class MachineTest extends Specification {

    Machine machine = new Machine()

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

    def "잘못된 버튼을 누른다"(){

        given: "잘못된 버튼 (여기선 리스트의 개수를 초과하는 6) 이 있다고 가정하고"
        int wantItem = 6

        when: "잘못된 버튼을 누를때"
        String buyItem = machine.buy(wantItem)

        then: "없음  이라는 멘트가 뜬다"
        buyItem == "없음"
    }

    def "지폐,동전을 투입한다"(){

        given: "천원짜리 1장, 500원짜리 3개, 100원짜리 2개가 있다고 가정하고"
        MoneyBean moneyBean = new MoneyBean()
        moneyBean.paper1000 = 1
        moneyBean.coin500 = 3
        moneyBean.coin100 = 2

        when: "투입할때"
        int money = machine.changeMoney(moneyBean)

        then: "2700원이다"
        money == 2700
    }

}
