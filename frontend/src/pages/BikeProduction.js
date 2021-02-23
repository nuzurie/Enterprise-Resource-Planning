import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import MainContainer from '../components/containers/MainContainer.js';
import BikeContainer from "../components/containers/BikeContainer.js";
import ToggleOnIcon from '@material-ui/icons/ToggleOn';


class BikeProduction extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <MainContainer title="Bikes">
            <div>
                <button><ToggleOnIcon /></button>
                <Title>In Progress</Title>
            </div>
            
            <BikeContainer>
              <Title>Bike #SerialID</Title>

            </BikeContainer>
            <BikeContainer>
              <Title>Bike #SerialID</Title>

            </BikeContainer>
        
            <BikeContainer>
              <Title>Bike #SerialID</Title>

            </BikeContainer>
          
            <BikeContainer>
              <Title>Bike #SerialID</Title>

            </BikeContainer>

            <BikeContainer>
              <Title>Bike #SerialID</Title>

            </BikeContainer>

        </MainContainer>
      
    );
  }
}


//STYLED-COMPONENTS


const TextInput = styled.input`
    border: 0;
    font-family: Montserrat;
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
const Title = styled.div`
    font-family: Montserrat;
    font-size: 10pt;
    color: black;
    text-transform: uppercase;
    letter-spacing: 0.2em;
    font-weight: 500;
    margin-top: 20px;
    
`



BikeProduction.propTypes = {
};

export default BikeProduction;