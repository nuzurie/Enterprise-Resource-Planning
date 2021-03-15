import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';

class InvoiceContainer extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <Container>
            <LeftHandside>
              <Title>{this.props.title}</Title>
              {/* {this.props.children} */}
              
              <GeneralInfoContainer>
                {this.props.userType} {this.props.userID} <br/>
                {this.props.amount} x {this.props.productName}
              </GeneralInfoContainer>

              <PriceContainer>
                {this.props.payType}: ${this.props.totalCost}
              </PriceContainer>
            </LeftHandside>
            <RightHandside hidden={this.props.readOnly}>
              <PayActionButton payAction={this.props.payAction}>
                {this.props.payAction}
              </PayActionButton>
              <StatusTitle>
                {this.props.productStatus}
              </StatusTitle>
            </RightHandside>
        </Container>
    );
  }
}

//STYLED-COMPONENTS
const Container = styled.div`
  background: #F9F9F9;
  padding: 15px;
  border-radius: 12px;
  margin-top: 15px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`

const LeftHandside = styled.div`
  & > div:nth-child(2), & > div:nth-child(3) {
    font-family: Proxima Nova;
    font-size: 7pt;
    font-weight: 100;
    margin-top: 5px;
    text-transform: uppercase;
    letter-spacing: 0.2em;
  }
`

const RightHandside = styled.div`
  display: ${props => props.hidden ? 'none' : 'flex'};
  flex-direction: column;
  align-items: flex-end;
`

const PayActionButton = styled.button`
  background-color: ${props => props.payAction == "NOT PAID" ? '#FF7A67' : '#3BC351'};
  color: white;
  font-family: Proxima Nova;
  font-size: 7pt;
  text-transform: uppercase;
  letter-spacing: 0.2em;
  border-radius: 4px;
  padding: 5px;
  margin-bottom: 5px;

  &:hover {
    background-color: ${props => props.payAction == "NOT PAID" ? '#F44A32' : '#3BC351'};
  }
`

const StatusTitle = styled.div`
  font-family: Proxima Nova;
  font-size: 8pt;
  color: #A3A3A3;
  text-transform: uppercase;
  letter-spacing: 0.2em;
  font-weight: 500;
`

const Title = styled.div`
  font-family: Proxima Nova;
  font-size: 8pt;
  color: black;
  text-transform: uppercase;
  letter-spacing: 0.2em;
  font-weight: 500;
`

const GeneralInfoContainer = styled.div`
  
`

const PriceContainer = styled.div`

`

InvoiceContainer.propTypes = {
    innerTitle: PropTypes.string.isRequired,
    children: PropTypes.element.isRequired,
};

export default InvoiceContainer;
