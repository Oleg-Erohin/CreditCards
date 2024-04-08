import './App.css';
import CardsContainer from './components/CardsContainer/CardsContainer';
import Header from './components/Header/Header';

function App() {

  return (
    <section>
      <header>
        <Header />
      </header>

      <main>
        <div className="App">
          <CardsContainer />
        </div>
      </main>
    </section>
  );
}

export default App;