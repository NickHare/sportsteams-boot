class App extends React.Component {
    constructor(props) {
            super(props);
            this.state = {
                error: null,
                isLoaded: false,
            };
            console.log('App constructor state: ' + JSON.stringify(this.state));
    }

    componentDidMount() {
        fetch('/players')
        .then((resp) => resp.json())
        .then((data) => {
            this.setState({players: data});
            console.log('fetch player state: ' + JSON.stringify(this.state));
        })
        .catch((e) => console.log(e));

        fetch('/teams')
        .then((resp) => resp.json())
        .then((data) => {
            this.setState({teams: data});
            console.log('fetch team state: ' + JSON.stringify(this.state));
        })
        .catch((e) => console.log(e));

        fetch('/rosters')
        .then((resp) => resp.json())
        .then((data) => {
            this.setState({rosters: data});
            console.log('fetch roster state: ' + JSON.stringify(this.state));
        })
        .catch((e) => console.log(e));
    }

    render() {
        return (
            <div className="app">
                <PlayerList players={this.state.players}/>
                <TeamList teams={this.state.teams}/>
                <RosterList players={this.state.players} teams={this.state.teams} rosters={this.state.rosters}/>
            </div>
        );
    }
}

console.log("Exporting App");