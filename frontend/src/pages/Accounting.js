import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import axios from 'axios';
import MainContainer from '../components/containers/MainContainer.js';
import Popup from "../components/Popup.js";
import InvoiceContainer from "../components/containers/InvoiceContainer.js";
import GradientButton from "../components/GradientButton.js"

class Accounting extends Component {
  constructor(props) {
    super(props);

    this.state = {
      showModal: false,

      invoiceCost: 0,
      invoiceID: 0,

      bikeInvoices: [],
      materialInvoices: [],
    }

    this.togglePaymentModal = this.togglePaymentModal.bind(this);
    this.getInvoiceDetails = this.getInvoiceDetails.bind(this);
    this.deductAmount = this.deductAmount.bind(this);
    this.initializeMaterialInvoices = this.initializeMaterialInvoices.bind(this);
  }

  togglePaymentModal() {
    this.setState({ showModal: !this.state.showModal });
  }

  deductAmount(e) {
    console.log(`Deduct ${this.state.invoiceCost}$ from account`);
    this.togglePaymentModal();

    axios.put(`/PurchaseOrders/${this.state.invoiceID}/MakePayment`);
  }

  getInvoiceDetails(cost, id) {
    console.log(`Current invoice has a total amount of: ${cost}$`);
    this.setState({
      invoiceCost: cost,
      invoiceID: id });
  }

  makeBikePayment() {
    console.log(this.state.invoiceID);
  }

  initializeMaterialInvoices() {
    axios.get('/PurchaseOrders')
    .then(res =>
      this.setState({
        materialInvoices: res.data }))
    .catch(err => console.log(err))

    axios.get('/SaleOrders')
    .then(res =>
      this.setState({
        bikeInvoices: res.data }))
    .catch(err => console.log(err))
  }

  componentDidMount() {
    this.initializeMaterialInvoices();
  }

  render() {
    let materialInvoices = <div></div>;
    let bikeInvoices = <div></div>;

    if (this.state.bikeInvoices.length !== 0) {
      bikeInvoices = this.state.bikeInvoices.map((invoice, index) => {
        return (
          <InvoiceContainer
            key={index}
            invoiceID={invoice.id}
            userName={invoice.client.name}
            userID={invoice.client.id}
            amount={invoice.saleOrderItems.length != 0 ? invoice.saleOrderItems[0].quantity : 1}
            productName={invoice.saleOrderItems.length != 0 ? invoice.saleOrderItems[0].bike.name : "untitled"}
            payType="Bike Cost"
            totalCost={invoice.grandTotal}
            payAction={invoice.paid ? "PAID" : "PAY"}
            productStatus={invoice.shipped ? "SHIPPED" : 'IN PROGRESS'} />
        );
      });
    }
    
    if (this.state.materialInvoices.length !== 0) {
      materialInvoices = this.state.materialInvoices.map((invoice, index) => {
        return (
          <InvoiceContainer
            key={index}
            invoiceID={invoice.id}
            userName={invoice.supplier.name}
            userID={invoice.supplier.id}
            amount={invoice.purchaseOrderItems.length != 0 ? invoice.purchaseOrderItems[0].quantity : 1}
            productName={invoice.purchaseOrderItems.length != 0 ? invoice.purchaseOrderItems[0].material.name : "untitled"}
            payType="Material Cost"
            totalCost={invoice.grandTotal}
            payAction={invoice.paid ? "PAID" : "PAY"}
            productStatus={invoice.received ? "RECEIVED" : "NOT RECEIVED"}
            showModal={this.togglePaymentModal}
            sendInvoiceCost={this.getInvoiceDetails} />
        );
      });
    }

    return (
      <Container>
        <PaymentPopup isVisible={this.state.showModal}>
          <Popup showModal={this.togglePaymentModal} title="ORDER PAYMENT" >
            <form onSubmit={this.deductAmount}>
              Order <InvoiceDetail>#{this.state.invoiceID}</InvoiceDetail> cost <InvoiceDetail>${this.state.invoiceCost}</InvoiceDetail>.
              <br/><br/>
              <InvoiceDetail>${this.state.invoiceCost}</InvoiceDetail> will be deducted from your account.
              <GradientButton type="submit" buttonValue="pay order" />
            </form>
          </Popup>
        </PaymentPopup>
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
              {/* <InvoiceContainer
                title="Bike Invoice ID"
                userType="Client"
                userID="123"
                amount={50}
                productName="Yas' Bike"
                payType="Bike Cost"
                totalCost={200}
                payAction="NOT PAID"
                productStatus="In Progress" /> */}
              {bikeInvoices}
            </MainContainer>
            <MainContainer title="Material Invoice">
              {materialInvoices}
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
    width: calc(100% - 40px); // TODO: fix fixed-width (100% should work, probably not accessing the right div)
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

const PaymentPopup = styled.div`
  position: absolute;
  z-index: 1;
  top: -20px;
  left: -101px;
  display: ${props => props.isVisible ? 'block' : 'none'};

  & > div > div > form > input {
    margin-top: 20px;
  }

  & > div > div {
    width: 275px;
  }
`

const InvoiceDetail = styled.div`
  font-weight: 500;
  color: #FF7A67;
  text-transform: uppercase;
  display: inline-block;
`