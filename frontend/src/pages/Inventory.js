import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import MainContainer from '../components/containers/MainContainer.js';

class Inventory extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Container>
        <MainContainer title="Bike parts" createFeature={true}>

        </MainContainer>
        <MainContainer title="Raw Material" createFeature={true}>
            
        </MainContainer>
      </Container>
    );
  }
}

//STYLED-COMPONENTS
const Container = styled.div`
  display: flex;
  flex-direction: row;
  height: 100%;

  & > div > div {
    margin-right: 20px;
  }
`

Inventory.propTypes = {
};

export default Inventory;
