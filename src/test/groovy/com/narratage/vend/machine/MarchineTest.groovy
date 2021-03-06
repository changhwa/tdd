package com.narratage.vend.machine

import spock.lang.Specification

class MachineTest extends Specification {

    Machine machine = new Machine()


    def "지폐,동전을 투입한다"(){

        given: "천원짜리 1장, 500원짜리 3개, 100원짜리 2개가 있다고 가정하고"
        MoneyBean moneyBean = new MoneyBean()
        moneyBean.paper1000 = 1
        moneyBean.coin500 = 3
        moneyBean.coin100 = 2

        when: "투입할때"
        int money = machine.insertMoney(moneyBean)

        then: "2700원이다"
        money == 2700
    }

    def "잘못된 버튼을 누른다"(){

        given: "잘못된 버튼 (여기선 리스트의 개수를 초과하는 6) 이 있다고 가정하고"
        int wantItem = 6

        when: "잘못된 버튼을 누를때"
        String buyItem = machine.buy(wantItem)

        then: "없음  이라는 멘트가 뜬다"
        buyItem == "없음"
    }

    def "음료수를 계산한다"(){

        given: "천원짜리 2장을 넣는다 가정하고"
        MoneyBean moneyBean = new MoneyBean()
        moneyBean.paper1000 = 2
        machine.insertMoney(moneyBean)

        when: "환타를 뽑을때"
        String itemName = machine.buy(3)

        then: "1200원이 계산이되고 800원이 남고, 환타가 나온다"
        itemName == "환타"
        machine.currentMoney == 800
    }

    def "부족한 돈을 넣고 음료수를 계산한다"(){

        given: "천원짜리 1장을 넣는다 가정하고"
        MoneyBean moneyBean = new MoneyBean()
        moneyBean.paper1000 = 1
        machine.insertMoney(moneyBean)

        when: "환타를 뽑을때"
        String itemName = machine.buy(3)

        then: "아무것도 나오지 않고, 현재 금액은 투입한 금액 그대로이다"
        itemName == ""
        machine.currentMoney == 1000
    }

    def "거스름돈을 받는다"(){

        given: "천원짜리 1장을 넣는다 가정하고"
        MoneyBean moneyBean = new MoneyBean()
        moneyBean.paper1000 = 1
        machine.insertMoney(moneyBean)

        when: "거스름돈 받기 버튼을 누를때"
        int returnMoney = machine.returnMoney()

        then: "거스름돈은 1000원 , 현재 금액은 0 원이다"
        returnMoney == 1000
        machine.currentMoney == 0
    }

}
