package nl.avans.hansvr.testing;

import org.junit.jupiter.api.Test;

import static nl.avans.hansvr.testing.AssignmentAvans.TypeOfShipping.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ShippingCostsTest {
    @Test
    public void shippingCostsShouldReturn0WhenCalculateShippingCostsIsFalse() {
        //given
        var calculateShippingCosts = false;
        var totalPrice = 0.0d;
        //when
        var result = AssignmentAvans.shippingCosts(calculateShippingCosts, NONE, totalPrice);
        //then
        assertThat(result, is(0.0d));
    }

    @Test
    public void shippingCostsShouldReturn0WhenTotalPriceIsBiggerThen1500AndCalculateShippingCostsIsTrue() {
        //given
        var calculateShippingCosts = true;
        var totalPrice = 1501d;
        //when
        var result = AssignmentAvans.shippingCosts(calculateShippingCosts, NONE, totalPrice);
        //then
        assertThat(result, is(0.0d));
    }

    @Test
    public void shippingCostsShouldReturn100WhenTotalPriceIsSmallerThen1500AndCalculateShippingCostsIsTrueAndTypeOfShippingCostsIsGround() {
        //given
        var calculateShippingCosts = true;
        var totalPrice = 1499d;
        //when
        var result = AssignmentAvans.shippingCosts(calculateShippingCosts, GROUND, totalPrice);
        //then
        assertThat(result, is(100d));
    }

    @Test
    public void shippingCostsShouldReturn50WhenTotalPriceIsSmallerThen1500AndCalculateShippingCostsIsTrueAndTypeOfShippingCostsIsInStore() {
        //given
        var calculateShippingCosts = true;
        var totalPrice = 1499d;
        //when
        var result = AssignmentAvans.shippingCosts(calculateShippingCosts, IN_STORE, totalPrice);
        //then
        assertThat(result, is(50d));
    }

    @Test
    public void shippingCostsShouldReturn250WhenTotalPriceIsSmallerThen1500AndCalculateShippingCostsIsTrueAndTypeOfShippingCostsIsNextDayAir() {
        //given
        var calculateShippingCosts = true;
        var totalPrice = 1499d;
        //when
        var result = AssignmentAvans.shippingCosts(calculateShippingCosts, NEXT_DAY_AIR, totalPrice);
        //then
        assertThat(result, is(250d));
    }

    @Test
    public void shippingCostsShouldReturn125WhenTotalPriceIsSmallerThen1500AndCalculateShippingCostsIsTrueAndTypeOfShippingCostsIsSecondDayAir() {
        //given
        var calculateShippingCosts = true;
        var totalPrice = 1499d;
        //when
        var result = AssignmentAvans.shippingCosts(calculateShippingCosts, SECOND_DAY_AIR, totalPrice);
        //then
        assertThat(result, is(125d));
    }

    @Test
    public void shippingCostsShouldReturn0WhenTotalPriceIsSmallerThen1500AndCalculateShippingCostsIsTrueAndTypeOfShippingCostsIsNone() {
        //given
        var calculateShippingCosts = true;
        var totalPrice = 1499d;
        //when
        var result = AssignmentAvans.shippingCosts(calculateShippingCosts, NONE, totalPrice);
        //then
        assertThat(result, is(0d));
    }
}
