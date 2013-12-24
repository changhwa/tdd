package com.narratage.vend.machine

class Machine {

    static List itemList = new ArrayList()

    static{

        itemSetUp("콜라",1000)
        itemSetUp("사이다",800)
        itemSetUp("환타",1200)
        itemSetUp("밀키스",500)
        itemSetUp("박카스",400)

    }

    static def void itemSetUp(def name, int value) {
        MachineBean machineBean = new MachineBean()
        machineBean.itemName = name
        machineBean.itemValue = value
        itemList.add(machineBean)
    }

    def buy(int itemIndex){
        if(itemList.size() <= itemIndex)
            return "없음"
        MachineBean machineBean = itemList.get(--itemIndex)
        machineBean.itemName
    }


    def changeMoney(MoneyBean money){
        money.totalMoney
    }

}
