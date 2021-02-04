import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';

class MainContainer extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <Container>
            <Title>{this.props.title}</Title>
            {this.props.children}
        </Container>
    );
  }
}

//STYLED-COMPONENTS
const Container = styled.div`
  background: white;
  padding: 20px;
  border-radius: 12px;
  height: calc(100% - 40px);
  box-shadow: 0 0 30px 0 rgba(43, 64, 104, 0.1);
`

const Title = styled.div`
    font-family: Montserrat;
    font-size: 12pt;
    color: black;
    text-transform: uppercase;
    letter-spacing: 0.2em;
    font-weight: 500;
`

MainContainer.propTypes = {
    title: PropTypes.string.isRequired,
    children: PropTypes.element.isRequired,
};

export default MainContainer;
