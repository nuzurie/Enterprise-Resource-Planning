import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import MainContainer from '../components/containers/MainContainer.js';
import RawMaterials from "../components/RawMaterials.js";
import Popup from "../components/Popup.js";

class Inventory extends Component {
  constructor(props) {
    super(props);

    this.state = {
      showModal: false,
    }

    this.toggleBikeModal = this.toggleBikeModal.bind(this);
  }

  toggleBikeModal() {
    this.setState({ showModal: !this.state.showModal });
  }

  render() {
    return (
      <Container>
        <AddBikeParts isVisible={this.state.showModal}>
          <Popup showModal={this.toggleBikeModal} title="Bike settings" buttonTitle="add bike(s)" > 
          </Popup>
        </AddBikeParts>
        <MainContainer title="Bike parts" createFeature={true} showModal={this.toggleBikeModal}>
          <RawMaterials title="road - 16x1 3/8in [iso 349]">
          </RawMaterials>
    
          <RawMaterials title="handle - mountain/silver">
          </RawMaterials>
        </MainContainer>
        
        <MainContainer title="Raw Material" createFeature={true} showModal={this.toggleBikeModal}>
          <RawMaterials title="rubber tire">
          </RawMaterials>
    
          <RawMaterials title="rim 700c">
          </RawMaterials>
        </MainContainer>
        
      </Container>
    );
  }
}

//STYLED-COMPONENTS
const Container = styled.div`
height: 100%;
border-radius: 0px;
display: flex;
flex-direction: row;

& > div {
  margin-right: 20px;
  width: 558px; //to remove once components are added into it
}
`

const AddBikeParts = styled.div`
  position: absolute;
  z-index: 1;
  top: -20px;
  left: -101px;
  display: ${props => props.isVisible ? 'block' : 'none'};
`




Inventory.propTypes = {
  
};

export default Inventory;