class RosterList extends React.Component {
    render(){
        console.log("players: " + JSON.stringify(this.props.players));
        console.log("teams: " + JSON.stringify(this.props.teams));
        console.log("rosters: " + JSON.stringify(this.props.rosters));
        const isLoaded = (this.props.players && this.props.teams && this.props.rosters) != undefined;
        console.log(isLoaded);
        if (!isLoaded){
            return (
                <div className="roster-list">
                    <p>loading...</p>
                </div>
            );
        }
        const players = this.props.players;
        const teams = this.props.teams;
        const rosters = this.props.rosters.map(
            (roster) => <Roster key={roster.id}
                roster={roster}
                player={players.find((player) => player.id == roster.playerId)}
                team={teams.find((team) => team.id == roster.teamId)}
            />
        );
        return (
            <div className="roster-list">
                <h1>Rosters</h1>

                <table className="styled-table">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Ext. Id</th>
                            <th>Player Name</th>
                            <th>Team Name</th>
                            <th>Created</th>
                            <th>Modified</th>
                        </tr>
                    </thead>
                    <tbody>
                        {rosters}
                    </tbody>
                </table>
            </div>
        );
    }
}

console.log("Exporting PlayerList");