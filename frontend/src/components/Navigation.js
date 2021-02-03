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
            {/* Enter icons here */

              <div>

                <div>
                  <button onClick={userProfile}><AccountCircleOutlinedIcon></AccountCircleOutlinedIcon> </button>
                </div>

                <br></br>
                <br></br>

                <div>
                  <button onClick={dashboard}><DashboardOutlinedIcon></DashboardOutlinedIcon> </button>
                </div>
                <div>
                  <button onClick={bike}><DirectionsBikeIcon></DirectionsBikeIcon></button>
                </div>

                <div>
                  <button onClick={inventory}><CardTravelOutlinedIcon></CardTravelOutlinedIcon></button>
                </div>
                
                <div>
                  <button onClick={maintenance}><SettingsIcon></SettingsIcon></button>
                </div>

                <div>
                  <button onClick={manufacturingPlan}><DateRangeOutlinedIcon></DateRangeOutlinedIcon> </button>
                </div>

                <div>
                  <button onClick={accounts}><DescriptionOutlinedIcon></DescriptionOutlinedIcon></button>
                </div>
              
              </div>

           
            }

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
  justify-content: center;
  align-items: center;

  button {
    background-color: Transparent;
    background-repeat:no-repeat;
    border: none;
    cursor:pointer;
    overflow: hidden;
    outline:none;
}
`

Navigation.propTypes = {
};

export default Navigation;
