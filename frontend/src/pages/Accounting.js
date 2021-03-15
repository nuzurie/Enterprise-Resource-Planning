import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import AccountingContainer from '../components/containers/AccountingContainer.js';
import MainContainer from '../components/containers/MainContainer.js';
// import BottomContainer from "../components/containers/BottomContainer.js";
import InnerContainer from "../components/containers/InnerContainer.js";
import InvoiceContainer from "../components/containers/InvoiceContainer.js";

class Accounting extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Container>
        <TopContainer>
          <MainContainer title="Accounts Payable">
            <InvoiceContainer
              title="Material Invoice ID"
              userType="Supplier"
              userID="321"
              amount={100}
              productName="brakes"
              payType="OWE"
              totalCost={200}
              readOnly />
          </MainContainer>

          <MainContainer title="Accounts Receivable">
            <InvoiceContainer
                title="Bike Invoice ID"
                userType="Client"
                userID="123"
                amount={50}
                productName="Yas' Bike"
                payType="OWED"
                totalCost={200}
                readOnly />
          </MainContainer>

          <InvoicesContainer>
            <MainContainer title="Bike Invoice">
                <InvoiceContainer
                  title="Bike Invoice ID"
                  userType="Client"
                  userID="123"
                  amount={50}
                  productName="Yas' Bike"
                  payType="Bike Cost"
                  totalCost={200}
                  payAction="NOT PAID"
                  productStatus="In Progress" />
            </MainContainer>
            <MainContainer title="Material Invoice">
            <InvoiceContainer
                  title="Material Invoice ID"
                  userType="Supplier"
                  userID="321"
                  amount={100}
                  productName="brakes"
                  payType="Material Cost"
                  totalCost={200}
                  payAction="PAY"
                  productStatus="Not Received" />
            </MainContainer>
          </InvoicesContainer>
        </TopContainer>

        <BottomContainer>
          <MainContainer title="Raw Cost">
          </MainContainer>
        </BottomContainer>
      </Container>
    );
  }
}

Accounting.propTypes = {
};

export default Accounting;

//STYLED-COMPONENTS
const TopContainer = styled.div`
  flex-direction: row;
  display: flex;
  flex: 2;
  width: 100%;
  // height: 100%;

  & > div {
    flex: 1;
    margin-right: 20px;
  }

  & > div:nth-child(3) {
    margin-right: 0;
  }
`

const InvoicesContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;

  & > div {
    flex: 1;
    width: calc(100% - 40px); // fix fixed-width (100% should work, probably not accessing the right div)
  }

  & > div:nth-child(2) {
    margin-top: 20px;
  }
`

const BottomContainer = styled.div`
  flex: 1;
  margin-top: 20px;
`

const Container = styled.div`
  height: 100%;
  width: 100%;
  border-radius: 0px;
  display: flex;
  flex-direction: column;
  position: relative;
`