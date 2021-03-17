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

import MatContainer from "../components/containers/MaterialsContainer.js";
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
        "material" :{
            "name": materialName,
            "cost": ramount
        },
        "quantity": 1
    }

    axios.post('/addMaterialToInventory', material);
    console.log(materialName + ": " + ramount);
  }


  render() {
    let materialList = <div></div>;
    let bikePartList = <div></div>;

    if (this.state.materials.length !== 0) {
      materialList = this.state.materials.map((element, index) => {
        return (
          <RawMaterials key={index} title={element.name} cost={element.cost} />
        );
      });
    }

    if (this.state.bikeParts.length !== 0) {
      bikePartList = this.state.bikeParts.map((element, index) => {
        return (
          <RawMaterials key={index} title={element.part.partType} type={element.part.type} cost={element.part.cost} amount={element.quantity} />
        );
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
          {/* <RawMaterials title="road - 16x1 3/8in [iso 349]">
          </RawMaterials>
    
          <RawMaterials title="handle - mountain/silver">
          </RawMaterials> */}
          {bikePartList}
        </MainContainer>
        
        <MainContainer title="Raw Material" createFeature={true} showModal={this.toggleMaterialModal}>
          {materialList}
          {/* <RawMaterials title="rubber tire">
          </RawMaterials>
    
          <RawMaterials title="rim 700c">
          </RawMaterials> */}
        </MainContainer>


        
          <MaterialsContainer>
            <MainContainer title="Raw Material Orders">
              <MatContainer
                title="Bike Invoice ID"
                userType="Client"
                userID="123"
                rawMaterial="Handle"
                totalCost={200}
                payAction="Received"
                productStatus="Not paid" />
            </MainContainer>
          </MaterialsContainer>
     
        <BottomContainer>
          <MainContainer title="Order Raw Material">
            <br />
            <form>
            <CustomDropdown dropdownName="SupplierID" dropddownID="SupplierID">
                <option value={"supplier id1"}>supplier id1</option>
                <option value={"supplier id2"}>supplier id2</option>
                <option value={"supplier id3"}>supplier id3</option>
                <option value={"supplier id4"}>supplier id4</option>
              </CustomDropdown>
              <br />
             
              <CustomDropdown dropdownName="MaterialID" dropddownID="MaterialID">
                <option value={"Material id1"}>Material id1</option>
                <option value={"Material id2"}>Material id2</option>
                <option value={"Material id3"}>Material id3</option>
                <option value={"Material id4"}>Material id4</option>
              </CustomDropdown>
              <br />

              <Title>Quantity</Title>
              <FieldContainer>
                <TextInput type="quantity" id="rquantity" name="rquantity" placeholder="quantity" min ="0"/>
              </FieldContainer>
              <br></br>
              <GradientButton type="submit" buttonValue="create order" />

            </form>
          </MainContainer>
        </BottomContainer>
        
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


const MaterialsContainer = styled.div`
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



Inventory.propTypes = {
  
};

export default Inventory;