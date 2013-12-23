package com.narratage.vend.machine

class Machine {

    static List itemList = new ArrayList()

    static{
        itemList.add("콜라")
        itemList.add("사이다")
        itemList.add("환타")
        itemList.add("밀키스")
        itemList.add("박카스")
    }

    def buy(int itemIndex){
        if((--itemList.size()) < itemIndex)
            return "없음"
        itemList.get(--itemIndex)
    }

}
