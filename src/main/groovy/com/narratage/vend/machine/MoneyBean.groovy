package com.narratage.vend.machine

class MoneyBean {

    int paper1000
    int paper5000
    int paper10000

    int coin100
    int coin500

    int getPaper1000() {
        return paper1000*1000
    }

    int getPaper5000() {
        return paper5000*5000
    }

    int getPaper10000() {
        return paper10000*10000
    }

    int getCoin100() {
        return coin100*100
    }

    int getCoin500() {
        return coin500*500
    }


}
