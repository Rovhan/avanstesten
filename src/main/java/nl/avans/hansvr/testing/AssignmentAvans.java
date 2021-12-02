package nl.avans.hansvr.testing;

public class AssignmentAvans {
    public static double shippingCosts(boolean calculateShippingCosts, TypeOfShipping typeOfShippingCosts, double totalPrice) {
        double result = 0.0d;
        if(calculateShippingCosts && totalPrice < 1500) {
            switch (typeOfShippingCosts) {
                case GROUND -> result = 100d;
                case IN_STORE -> result = 50d;
                case NEXT_DAY_AIR -> result = 250d;
                case SECOND_DAY_AIR -> result = 125d;
                case NONE -> result = 0d;
            }
        }
        return result;
    }

    enum TypeOfShipping {
        GROUND, IN_STORE, NEXT_DAY_AIR, SECOND_DAY_AIR, NONE
    }
}

