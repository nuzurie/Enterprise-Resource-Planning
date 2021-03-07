import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import AccountingContainer from '../components/containers/AccountingContainer.js';

class Accounting extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Container>
        <AccountingContainer title="Accounts Payable">
            
        </AccountingContainer>

        <AccountingContainer title="Accounts Receivable">
            
        </AccountingContainer>

        <AccountingContainer title="Invoice">
            
        </AccountingContainer>
        </Container>
        
    );
  }
}

//STYLED-COMPONENTS

Accounting.propTypes = {
};

export default Accounting;

const Container = styled.div`
height: 100%;
border-radius: 0px;
display: flex;
flex-direction: row;
position: relative;

& > div {
  margin-right: 20px;
}
`