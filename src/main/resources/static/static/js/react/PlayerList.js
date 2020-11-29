class PlayerList extends React.Component {
    render(){
        if (!this.props.players){
            return (
                <div className="player-list">
                    <p>loading...</p>
                </div>
            );
        }
        const players = this.props.players.map(player => <Player key={player.id} player={player}/>);
        return (
            <div className="player-list">
                <h1>Players</h1>

                <table className="styled-table">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Ext. Id</th>
                            <th>Name</th>
                            <th>Created</th>
                            <th>Modified</th>
                        </tr>
                    </thead>
                    <tbody>
                        {players}
                    </tbody>
                </table>
            </div>
        );
    }
}

console.log("Exporting PlayerList");