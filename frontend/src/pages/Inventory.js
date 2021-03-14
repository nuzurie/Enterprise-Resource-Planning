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
import axios from "axios";

class Inventory extends Component {
  constructor(props) {
    super(props);

    this.state = {
      showBikePartModal: false,
      showRawMatModal: false,
      materials: [],
      bikeParts: [],
    }

    this.toggleBikeModal = this.toggleBikeModal.bind(this);
    this.toggleMaterialModal = this.toggleMaterialModal.bind(this);
    this.addMaterial = this.addMaterial.bind(this);
  }

  componentDidMount() {
    this.initializeInventoryLists();
  }

  initializeInventoryLists() {
    axios.get('/materials')
    .then(res =>
      this.setState({
        materials: res.data._embedded.materialList, 
      }))
    .catch(err => console.log(err));

    axios.get('/plants')
    .then(res =>
      this.setState({
        bikeParts: res.data._embedded.plantList[0].parts, 
      }))
    .catch(err => console.log(err));
  }

  toggleBikeModal() {
    this.setState({ showBikePartModal: !this.state.showBikePartModal });
  }

  toggleMaterialModal() {
    this.setState({ showRawMatModal: !this.state.showRawMatModal });
  }

  addMaterial(e) {
    // e.preventDefault();

    const form = new FormData(e.target);
    const materialName = form.get("materialName");
    const ramount = form.get("ramount");

    let material = {
      "name": materialName,
      "cost": ramount
    }

    axios.post('/materials', material)
    .then(res =>
      console.log(res.data))
    .catch(err => console.log(err));
  }


  render() {
    let materialList = <div></div>;
    let bikePartList = <div></div>;
    let materialsForParts = <div></div>;

    if (this.state.materials.length !== 0) {
      materialsForParts = this.state.materials.map((element, index) => {
        return (
          <CustomRadioButton key={index} id={element.name} value={element.name}>{element.name}</CustomRadioButton>
        );
      });
    }

    if (this.state.materials.length !== 0) {
      materialList = this.state.materials.map((element, index) => {
        return (
          <RawMaterials key={index} title={element.name} cost={element.cost} />
        );
      });
    }

    if (this.state.bikeParts.length !== 0) {
      bikePartList = this.state.bikeParts.map((element, index) => {
        if (element.part.partType === "frame") {
          return (
            <RawMaterials key={index} title={element.part.partType} type={`${element.part.size} | ${element.part.colour}`} cost={`${element.part.cost}$`} amount={element.quantity} />
          );
        } else {
          return (
            <RawMaterials key={index} title={element.part.partType} type={element.part.type} cost={`${element.part.cost}$`} amount={element.quantity} />
          );
        }
      });
    }

    console.log(this.state.materials);
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
                {materialsForParts}
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
           <form onSubmit={this.addMaterial}>
              <FieldContainer>
                <TextInput type="text" id="materialName" name="materialName" placeholder="material name" />
              </FieldContainer>
                {/* <CustomDropdown dropdownName="bikeParts" dropddownID="bikeParts">
                  <option value={"HANDLE"}>handle</option>
                  <option value={"SEAT"}>seat</option>
                  <option value={"FRAMES"}>frames</option>
                  <option value={"WHEEL"}>wheel</option>
                </CustomDropdown> */}

              <Title>Amount</Title>
              <FieldContainer>
                <TextInput type="number" id="ramount" name="ramount" placeholder="amount" min ="0" step="0.01" />
              </FieldContainer>
              <GradientButton type="submit" buttonValue="add raw material" />
           </form>
          </Popup>
        </AddRawMaterials>


        <MainContainer title="Bike parts" createFeature={true} showModal={this.toggleBikeModal}>
          <Legend>
            <div>Part</div>
            <div>Type</div>
            <div>Cost</div>
            <div>Qty</div>
          </Legend>
          {bikePartList}
        </MainContainer>
        
        <MainContainer title="Raw Material" createFeature={true} showModal={this.toggleMaterialModal}>
          <Legend>
            <div>Part</div>
          </Legend>
          {materialList}
          {/* <RawMaterials title="rubber tire">
          </RawMaterials>
    
          <RawMaterials title="rim 700c">
          </RawMaterials> */}
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

  & > div > div > form > div:nth-child(3) {
    padding: 0 0 10px 10px;
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

const Legend = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 10px;
  background: white;

  div {
    font-family: Proxima Nova;
  }

  div:nth-child(1), div:nth-child(2) {
    min-width: 120px;
  }

  div:nth-child(3) {
    width: 50px;
  }

  div:nth-child(4) {
    width: 50px;
  }
`


Inventory.propTypes = {
  
};

export default Inventory;