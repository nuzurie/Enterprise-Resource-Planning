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

  render() {
    return (
        <LogisticsContainer title="Logistics">
            <InnerContainer>
              <CustomRadioButton value="Inventory">Inventory</CustomRadioButton>
              <CustomRadioButton value="Manufacturing">Manufacturing</CustomRadioButton>
              <CustomRadioButton value="Accounting">Accounting</CustomRadioButton>
              <CustomRadioButton value="All">All</CustomRadioButton>
            </InnerContainer>
            
        </LogisticsContainer>
        
    );
  }
}

//STYLED-COMPONENTS

Logistics.propTypes = {
};

export default Logistics;
