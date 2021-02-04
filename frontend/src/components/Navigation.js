import React, { Component } from "react";
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




function userProfile() {
  alert('This is the profile page!');
}

function dashboard() {
  alert('This is the dashboard!');
}

function bike() {
  alert('This is the bike section!');
}

function inventory() {
  alert('This is the inventory page!');
}

function maintenance() {
  alert('This is the maintenance page!');
}

function manufacturingPlan() {
  alert('This is the manufacturing plan page!');
}

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
              <button onClick={userProfile}><AccountCircleOutlinedIcon /></button>
            </div>
            <div>
              <button onClick={dashboard}><DashboardOutlinedIcon /></button>
            </div>
            <div>
              <button onClick={bike}><DirectionsBikeIcon /></button>
            </div>

            <div>
              <button onClick={inventory}><CardTravelOutlinedIcon /></button>
            </div>
            
            <div>
              <button onClick={maintenance}><SettingsIcon /></button>
            </div>

            <div>
              <button onClick={manufacturingPlan}><DateRangeOutlinedIcon /></button>
            </div>

            <div>
              <button onClick={accounts}><DescriptionOutlinedIcon /></button>
            </div>

            <div>
              <button onClick={accounts}><AddUser /></button>
            </div>
          </div>
          <div>
            <button onClick={accounts}><Logout /></button>
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

  &:nth-child(1) {
    margin-bottom: 40px;
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
