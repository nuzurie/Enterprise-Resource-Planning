import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import ExpandLessMore from '@material-ui/icons/ExpandMore';
import FrameIcon from '../../icons/bikeframe.svg';
import FormatPaintIcon from '@material-ui/icons/FormatPaint';
import LayersIcon from '@material-ui/icons/Layers';
import HandleIcon from '../../icons/handlebar.svg';
import PedalIcon from '../../icons/pedal.svg';
import BikeProgress from '../BikeProgress.js'

class BikeContainer extends Component {
  constructor(props) {
    super(props);

     this.state = {
      showExpansion: false,
      progressLevel: "assemble",
    }

    this.toggleExpansion = this.toggleExpansion.bind(this);
  }

  toggleExpansion() {

  }

  render() {
    return (
        <Container>
            <Header>
              <Title>{this.props.title}</Title>
              <ExpandButton onClick={this.props.toggleExpansion} isVisible={this.props.createFeature}>
                <ExpandLessMore />
              </ExpandButton>
            </Header>
            <CharacteristicContainer>
              <Characteristic>
                <img src={FrameIcon} />
                <PartTitle>small</PartTitle>
              </Characteristic>
              <Characteristic>
                <ColorIcon frameColor={this.props.frameColor} />
                <PartTitle>blue</PartTitle>
              </Characteristic>
              <Characteristic>
                <FormatPaintIcon color="#BBC8E3"/>
                <PartTitle>matte</PartTitle>
              </Characteristic>
              <Characteristic>
                <LayersIcon color="#BBC8E3"/>
                <PartTitle>carbon</PartTitle>
              </Characteristic>
              <Characteristic>
                <img src={HandleIcon} />
                <PartTitle>dropbar</PartTitle>
              </Characteristic>
              <Characteristic>
                <img src={PedalIcon} />
                <PartTitle>straight</PartTitle>
              </Characteristic>
            </CharacteristicContainer>
            {/* {this.props.children} */}
            <BikeProgress progressLevel={this.state.progressLevel} />
        </Container>
    );
  }
}

//STYLED-COMPONENTS
const Container = styled.div`
  background: #F9F9F9;
  padding: 15px;
  border-radius: 12px;
  height: calc(100%-109px);
  width: 506px;
  margin-top: 15px;
`

const Header = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`

const Title = styled.div`
    font-family: Montserrat;
    font-size: 9pt;
    color: black;
    text-transform: uppercase;
    letter-spacing: 0.2em;
    font-weight: 500;
`

const ExpandButton = styled.div`
  background-color: transparent;
  background-repeat: no-repeat;
  border: none;
  cursor: pointer;
  overflow: hidden;
  outline: none;
  color: #BBC8E3;
  transition: 250ms;
  display: block;

  &:hover, &::selection {
    color: #BBC8E3;
  }

  svg {
    width: 1.2em;
    height: 1.2em;
  }
`

const CharacteristicContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
`

const Characteristic = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;

  & > img {
    width: 3em;
    height: 3em;
  }

  & > svg {
    width: 1.7em;
    height: 1.7em;
    color: #BBC8E3;
  }
`

const PartTitle = styled.div`
  font-family: Montserrat;
  font-weight: 400;
  font-size: 8pt;
  letter-spacing: 0.2em;
  color: #556C99;
  text-transform: uppercase;
`

const ColorIcon = styled.div`
  border-radius: 100%;
  width: 2.5em;
  height: 2.5em;
  background-color: blue;
`

BikeContainer.propTypes = {
    innerTitle: PropTypes.string.isRequired,
    children: PropTypes.element.isRequired,
};

export default BikeContainer;
