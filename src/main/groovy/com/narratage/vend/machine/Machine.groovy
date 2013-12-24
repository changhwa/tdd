package com.narratage.vend.machine

class Machine {

    static List itemList = new ArrayList()

    static{

        MachineBean machineBean = new MachineBean()
        machineBean.itemName = "콜라"
        machineBean.itemValue = 1000
        itemList.add(machineBean)

        machineBean = new MachineBean()
        machineBean.itemName = "사이다"
        machineBean.itemValue = 800
        itemList.add(machineBean)

        machineBean = new MachineBean()
        machineBean.itemName = "환타"
        machineBean.itemValue = 1200
        itemList.add(machineBean)

        machineBean = new MachineBean()
        machineBean.itemName = "밀키스"
        machineBean.itemValue = 500
        itemList.add(machineBean)

        machineBean = new MachineBean()
        machineBean.itemName = "박카스"
        machineBean.itemValue = 400
        itemList.add(machineBean)

    }

    def buy(int itemIndex){
        if(itemList.size() <= itemIndex)
            return "없음"
        MachineBean machineBean = itemList.get(--itemIndex)
        machineBean.itemName
    }


    def changeMoney(MoneyBean money){
        money.coin100+money.coin500+money.paper1000+money.paper5000+money.paper10000
    }

}
