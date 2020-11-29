class TeamList extends React.Component {
    render(){
        if (!this.props.teams){
            return (
                <div className="team-list">
                    <p>loading...</p>
                </div>
            );
        }
        const teams = this.props.teams.map(team => <Team key={team.id} team={team}/>);
        return (
            <div className="team-list">
                <h1>Teams</h1>
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
                        {teams}
                    </tbody>
                </table>
            </div>
        );
    }
}

console.log("Exporting TeamList");