import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import MainContainer from '../components/containers/MainContainer.js';
import InnerContainer from '../components/containers/InnerContainer.js';
import CustomRadioButton from '../components/CustomRadioButton.js';

class Logistics extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <MainContainer title="Logistics">
            <InnerContainer>
              <CustomRadioButton value="Inventory">Inventory</CustomRadioButton>
              <CustomRadioButton value="Manufacturing">Manufacturing</CustomRadioButton>
              <CustomRadioButton value="Accounting">Accounting</CustomRadioButton>
              <CustomRadioButton value="All">All</CustomRadioButton>
            </InnerContainer>
            
        </MainContainer>
        
    );
  }
}

//STYLED-COMPONENTS

Logistics.propTypes = {
};

export default Logistics;
