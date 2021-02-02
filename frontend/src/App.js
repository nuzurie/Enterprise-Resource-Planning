import './App.css';
import styled from 'styled-components';
import NavigationContainer from './components/Navigation.js';


function App() {
  return (
    <Container>
      <NavigationContainer />
      <Content>
        {/* Insert selected page component here */}
      </Content>
    </Container>
  );
}

//STYLED-COMPONENTS
const Container = styled.div`
  background: #F2F5FC;
  height: 100vh;
  display: flex;
  flex-direction: row;
`

const Content = styled.div`
  padding: 20px;
`

export default App;
