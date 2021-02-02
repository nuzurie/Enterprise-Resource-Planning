import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';

class Navigation extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <NavigationContainer>
            {/* Enter icons here */}
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
`

Navigation.propTypes = {
};

export default Navigation;
