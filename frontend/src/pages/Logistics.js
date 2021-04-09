import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import MainContainer from '../components/containers/MainContainer.js';
import LogisticsContainer from '../components/containers/LogisticsContainer.js';
import InnerContainer from '../components/containers/InnerContainer.js';
import CustomRadioButton from '../components/CustomRadioButton.js';

class Logistics extends Component {
  constructor(props) {
    super(props);
  }

  test() {
    console.log("test");
  }

  render() {
    return (
        <LogisticsContainer title="Logistics">
            <InnerContainer>
              <ChoicesContainer onSubmit={this.test}>
                <CustomRadioButton name="logsDisplay" value="Inventory" onChange={this.form.submit()}>Inventory</CustomRadioButton>
                <CustomRadioButton name="logsDisplay" value="Manufacturing" onChange={this.form.submit()}>Manufacturing</CustomRadioButton>
                <CustomRadioButton name="logsDisplay" value="Accounting" onChange={this.form.submit()}>Accounting</CustomRadioButton>
                <CustomRadioButton name="logsDisplay" value="All" onChange={this.form.submit()}>All</CustomRadioButton>
              </ChoicesContainer>
            </InnerContainer>
            
        </LogisticsContainer>
        
    );
  }
}

//STYLED-COMPONENTS
const ChoicesContainer = styled.form`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
`

Logistics.propTypes = {
};

export default Logistics;
