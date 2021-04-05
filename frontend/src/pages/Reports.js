import React, { Component } from "react";
import PropTypes from "prop-types";
import styled from 'styled-components';
import MainContainer from '../components/containers/MainContainer.js';
import GradientButton from "../components/GradientButton.js"
import CustomDropdown from "../components/CustomDropdown";
import CustomRadioButton from "../components/CustomRadioButton";

class Reports extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Container>
        <MainContainer title="Quality Data">
            
        </MainContainer>
        <ReportsContainer>
          <TopContainer>
            <Report title="Download">
              <DocumentForm>
                <div>
                  <Caption>Select the document you wish to download from the cloud.</Caption>
                  <CustomDropdown dropdownName="bikeSize" dropddownID="bikeSize">
                    <option value={24.0}>small</option>
                    <option value={26.0}>medium</option>
                    <option value={28.0}>large</option>
                  </CustomDropdown>
                </div>
                <GradientButton GradientButton type="submit" buttonValue="download document"/>
              </DocumentForm>
            </Report>
            <Report title="Upload">
              <DocumentForm>
                <div>
                  <Caption>Choose the file you wish to upload to the cloud.</Caption>
                  <input type="file"  />
                </div>
                <GradientButton GradientButton type="submit" buttonValue="upload document"/>
              </DocumentForm>
            </Report>
          </TopContainer>
          <Report title="Generate">
            <DocumentForm>
              <div>
                <Caption>Generate an updated document directly from the records.</Caption>
                <Title>Document Type</Title>
                <CustomRadioButton value="ledger" name="documentType">Ledger (all)</CustomRadioButton>
                <CustomRadioButton value="saleOrders" name="documentType">Sale Orders</CustomRadioButton>
                <CustomRadioButton value="purchaseOrders" name="documentType">Purchase Orders</CustomRadioButton>
                
                <Title>Document Format</Title>
                <CustomRadioButton value="pdf" name="documentFormat">PDF</CustomRadioButton>
                <CustomRadioButton value="csv" name="documentFormat">CSV</CustomRadioButton>
              </div>
              <GradientButton GradientButton type="submit" buttonValue="generate document"/>
            </DocumentForm>
          </Report>
        </ReportsContainer>
      </Container>
    );
  }
}

//STYLED-COMPONENTS
const Container = styled.div`
  display: flex;
  flex-direction: row;
  justify-contents: space-between;
  height: 100%;

  & > div:nth-child(2) {
    margin-left: 20px;
  }

  & > div {
    flex: 1;
  }
`

const ReportsContainer = styled.div`
  display: flex;
  flex-direction: column;

  & > div:nth-child(2) {
    margin-top: 20px;
    width: calc(100% - 40px);
  }

  & > div {
    flex: 1;
  }
`

const TopContainer = styled.div`
  display: flex;
  flex-direction: row;

  & > div {
    flex: 1;
  }

  & > div:nth-child(2) {
    margin-left: 20px;
  }
`

const Report = styled(MainContainer)`
  & > div {
    display: flex;
    justify-content: space-between;
    background: red;
  }
`

const Title = styled.div`
  margin-top: 20px;
  font-size: 10pt;
  color: black;
  text-transform: uppercase;
  letter-spacing: 0.2em;
  font-weight: 500;
`

const Caption = styled.div`
  margin-bottom: 10px;
`

const DocumentForm = styled.form`
  display: flex;
  flex-direction: column;
  height: calc(100% - 20px);
  justify-content: space-between;
  margin-top: 20px;
`

Reports.propTypes = {
};

export default Reports;
