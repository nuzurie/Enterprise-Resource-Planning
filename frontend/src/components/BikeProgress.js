import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import StepProgressBar from 'react-step-progress';
import 'react-step-progress/dist/index.css';

class BikeProgress extends Component {
  constructor(props) {
    super(props);
  }

  gatherValidator() {
    return true;
  }

  makeValidator() {
    return true;
  }

  shippingValidator() {
    return true;
  }

  onFormSubmit() {
    console.log("hello we are in the step progress");
  }

  render() {
    const step1Content = <h1>Step 1 Content</h1>;
    const step2Content = <h1>Step 2 Content</h1>;
    const step3Content = <h1>Step 3 Content</h1>;

    return (
        <Container
            startingStep={0}
            onSubmit={this.onFormSubmit}
            steps={[
                {
                    label: 'gather',
                    name: 'step 1',
                    content: step1Content,
                    onSubmit: this.gatherValidator
                },
                {
                    label: 'make',
                    name: 'step 2',
                    content: step2Content,
                    validator: this.makeValidator,
                },
                {
                    label: 'shipping',
                    name: 'step 3',
                    content: step3Content,
                    validator: this.shippingValidator
                },
            ]}
        />
    );
  }
}

//STYLED-COMPONENTS
const Container = styled(StepProgressBar)`
  width: 100%;

  & > ul {
    margin-top: 35px;
  }
`

BikeProgress.propTypes = {
    
};

export default BikeProgress;
