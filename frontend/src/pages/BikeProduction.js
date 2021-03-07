import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import MainContainer from '../components/containers/MainContainer.js';
import BikeContainer from "../components/containers/BikeContainer.js";
import Popup from "../components/Popup.js";
import CustomDropdown from "../components/CustomDropdown";
import GradientButton from "../components/GradientButton.js"

import ToggleOnIcon from '@material-ui/icons/ToggleOn';

class BikeProduction extends Component {
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
      //TODO: Separate containers into components
      <Container>
        <AddBikePopup isVisible={this.state.showModal}>
          <Popup showModal={this.toggleBikeModal} title="Bike settings" > 
            <form>
              <CustomDropdown dropdownName="bikeSize" dropddownID="bikeSize">
                <option value={0}>small</option>
                <option value={1}>medium</option>
                <option value={2}>large</option>
              </CustomDropdown>
              <CustomDropdown dropdownName="bikeColor" dropddownID="bikeColor">
                <option value={"RED"}>red</option>
                <option value={"BLUE"}>blue</option>
                <option value={"GREEN"}>green</option>
                <option value={"ORANGE"}>orange</option>
                <option value={"SILVER"}>silver</option>
                <option value={"BLACK"}>black</option>
              </CustomDropdown>
              <CustomDropdown dropdownName="bikeFinish" dropddownID="bikeFinish">
                <option value={"MATTE"}>matte</option>
                <option value={"CHROME"}>chrome</option>
              </CustomDropdown>
              <CustomDropdown dropdownName="bikeGrade" dropddownID="bikeGrade">
                <option value={"STEEL"}>chrome</option>
                <option value={"ALUMINIUM"}>aluminium</option>
                <option value={"CARBON"}>carbon</option>
              </CustomDropdown>
              <CustomDropdown dropdownName="bikeHandlebar" dropddownID="bikeHandlebar">
                <option value={"DROPBAR"}>dropbar</option>
                <option value={"STRAIGHT"}>straight</option>
                <option value={"BULLHORN"}>bullhorn</option>
              </CustomDropdown>
              <CustomDropdown dropdownName="bikePedal" dropddownID="bikePedal">
                <option value={"STRAP"}>strap</option>
                <option value={"CLIP"}>clip</option>
              </CustomDropdown>
              <GradientButton type="submit" buttonValue="add bike(s)" />
            </form>
          </Popup>
        </AddBikePopup>
        <MainContainer title="Bikes" createFeature={true} showModal={this.toggleBikeModal}>
          {/* TODO: Add interactive toggle
              <div>
              <button><ToggleOnIcon /></button>
              <Title>In Progress</Title>
          </div> */}
          
          <BikeContainer title="Bike #SerialID" />

        </MainContainer>
      </Container>
    );
  }
}


//STYLED-COMPONENTS
const Container = styled.div`
  position: relative;
  height: 100%;
`

const Title = styled.div`
    font-family: Proxima Nova;
    font-size: 10pt;
    color: black;
    text-transform: uppercase;
    letter-spacing: 0.2em;
    font-weight: 500;
    margin-top: 20px;
    
`

const AddBikePopup = styled.div`
  position: absolute;
  z-index: 2; //over bike progress
  top: -20px;
  left: -101px;
  display: ${props => props.isVisible ? 'block' : 'none'};

  & > div > div > form > input {
    margin-top: 20px;
  }
`

BikeProduction.propTypes = {
};

export default BikeProduction;