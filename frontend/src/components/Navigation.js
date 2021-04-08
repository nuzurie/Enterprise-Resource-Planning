import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import styled from 'styled-components';
import AccountCircleOutlinedIcon from '@material-ui/icons/AccountCircleOutlined';
import DashboardOutlinedIcon from '@material-ui/icons/DashboardOutlined';
import DateRangeOutlinedIcon from '@material-ui/icons/DateRangeOutlined'
import CardTravelOutlinedIcon from '@material-ui/icons/CardTravelOutlined';
import DescriptionOutlinedIcon from '@material-ui/icons/DescriptionOutlined';
import DirectionsBikeIcon from '@material-ui/icons/DirectionsBike';
import SettingsIcon from '@material-ui/icons/Settings';
import AddUser from '@material-ui/icons/PersonAdd';
import Logout from '@material-ui/icons/PowerSettingsNew';

function accounts() {
  alert('This is the accounts page!');
}

class Navigation extends Component {
  constructor(props) {
    super(props);
   
  }

  render() {
    return (
        <NavigationContainer>
          <div>
            <div>
              <Link to="/login">
                <button><AccountCircleOutlinedIcon /></button>
              </Link>
            </div>
            <div>
              <Link to="/dashboard">
                <button><DashboardOutlinedIcon /></button>
              </Link>
            </div>
            <Link to="/bikeproduction">
              <button><DirectionsBikeIcon /></button>
            </Link>

            <div>
              <Link to="/inventory">
                <button><CardTravelOutlinedIcon /></button>
              </Link>
            </div>
            
            <div>
              <Link to="/logistics">
                <button><SettingsIcon /></button>
              </Link>
            </div>

            <div>
              <Link to="/manufacturing">
                <button><DateRangeOutlinedIcon /></button>
              </Link>
            </div>

            <div>
              <Link to="/accounting">
                <button><DescriptionOutlinedIcon /></button>
              </Link>
            </div>

            <Link to="/createuser">
              <button><AddUser /></button>
            </Link>
          </div>
          <div>
            <button><Logout /></button>
          </div>
        </NavigationContainer>
    );
  }
}

//STYLED-COMPONENTS
const NavigationContainer = styled.div`
  background: white;
  padding: 20px;
  height: calc(100vh - 40px);
  box-shadow: 0 0 30px 0 rgba(43, 64, 104, 0.1);
  justify-content: space-between;
  align-items: center;
  display: flex;
  flex-direction: column;

  & div:first-of-type {
    margin-bottom: 50px;
  }

  button {
    background-color: transparent;
    background-repeat: no-repeat;
    border: none;
    cursor: pointer;
    overflow: hidden;
    outline: none;
    color: #E0E6F1;
    transition: 250ms;
    margin-top: 20px;

    &:hover, &::selection {
      color: #BBC8E3;
    }

    svg {
      width: 1.2em;
      height: 1.2em;
    }
}
`

Navigation.propTypes = {
};

export default Navigation;
