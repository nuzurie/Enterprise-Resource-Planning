import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import MainContainer from '../components/containers/MainContainer.js';
import RawMaterials from "../components/RawMaterials.js";
import Popup from "../components/Popup.js";
import CustomDropdown from "../components/CustomDropdown";
import CustomRadioButton from "../components/CustomRadioButton";
import FieldContainer from '../components/containers/FieldContainer.js';
import GradientButton from '../components/GradientButton';

class Inventory extends Component {
  constructor(props) {
    super(props);

    this.state = {
      showBikePartModal: false,
      showRawMatModal: false,
    }

    this.toggleBikeModal = this.toggleBikeModal.bind(this);
    this.toggleMaterialModal = this.toggleMaterialModal.bind(this);
  }

  toggleBikeModal() {
    this.setState({ showBikePartModal: !this.state.showBikePartModal });
  }

  toggleMaterialModal() {
    this.setState({ showRawMatModal: !this.state.showRawMatModal });
  }


  render() {
    return (
      <Container>
        <AddBikeParts isVisible={this.state.showBikePartModal}>
          <Popup showModal={this.toggleBikeModal} title="Bike Part Settings" buttonTitle="add bike(s)" > 
           <form>
              <CustomDropdown dropdownName="bikeParts" dropddownID="bikeParts">
                <option value={"HANDLE"}>handle</option>
                <option value={"SEAT"}>seat</option>
                <option value={"FRAMES"}>frames</option>
                <option value={"WHEEL"}>wheel</option>
              </CustomDropdown>

              <Title>Materials Needed</Title>
              <FieldContainer>
                <CustomRadioButton value="gears">Gears</CustomRadioButton>
              </FieldContainer>

              <Title>Amount</Title>
              <FieldContainer>
                <TextInput type="number" id="bamount" name="bamount" placeholder="amount" min ="0"/>
              </FieldContainer>
              <GradientButton type="submit" buttonValue="add bike part" />
           </form>
          </Popup>
        </AddBikeParts>
        

        <AddRawMaterials isVisible={this.state.showRawMatModal}>
          <Popup showModal={this.toggleMaterialModal} title="Raw Material" buttonTitle="add bike(s)" > 
           <form>
                <CustomDropdown dropdownName="bikeParts" dropddownID="bikeParts">
                  <option value={"HANDLE"}>handle</option>
                  <option value={"SEAT"}>seat</option>
                  <option value={"FRAMES"}>frames</option>
                  <option value={"WHEEL"}>wheel</option>
                </CustomDropdown>

              <Title>Amount</Title>
              <FieldContainer>
                <TextInput type="number" id="ramount" name="ramount" placeholder="amount" min ="0"/>
              </FieldContainer>
              <GradientButton type="submit" buttonValue="add raw material" />
           </form>
          </Popup>
        </AddRawMaterials>


        <MainContainer title="Bike parts" createFeature={true} showModal={this.toggleBikeModal}>
          <RawMaterials title="road - 16x1 3/8in [iso 349]">
          </RawMaterials>
    
          <RawMaterials title="handle - mountain/silver">
          </RawMaterials>
        </MainContainer>
        
        <MainContainer title="Raw Material" createFeature={true} showModal={this.toggleMaterialModal}>
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
position: relative;

& > div {
  margin-right: 20px;
}
`
const Title = styled.div`
    font-family: Proxima Nova;
    font-size: 8pt;
    color: black;
    text-transform: uppercase;
    letter-spacing: 0.2em;
    font-weight: 500;
    margin-top: 20px;
`

const AddBikeParts = styled.div`
  position: absolute;
  z-index: 1;
  top: -20px;
  left: -101px;
  display: ${props => props.isVisible ? 'block' : 'none'};

  & > div > div > form > input {
    margin-top: 20px;
  }
`

const AddRawMaterials = styled.div`
  position: absolute;
  z-index: 1;
  top: -20px;
  left: -101px;
  display: ${props => props.isVisible ? 'block' : 'none'};

  & > div > div > form > input {
    margin-top: 20px;
  }
`

const TextInput = styled.input`
border: 0;
font-family: Proxima Nova;
font-size: 9pt;
color: #556C99;
text-transform: uppercase;
letter-spacing: 0.2em;
font-weight: 500;
margin: 8px;
width: 400px;

&:focus {
    outline: none;
}

::placeholder {
    color: #BBC8E3;
}
`




Inventory.propTypes = {
  
};

export default Inventory;