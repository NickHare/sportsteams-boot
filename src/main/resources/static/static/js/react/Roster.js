class Roster extends React.Component {
    toDateString(date){
        var options = {year: 'numeric', month: '2-digit', day: '2-digit', hour: 'numeric', minute: 'numeric', second: 'numeric'};
        options.timeZone = 'UTC';
        const string = new Intl.DateTimeFormat('en', options).format(date);
        return string;
    }

    render(){
        const id = this.props.roster.id || "-";
        const externalId = this.props.roster.externalId || "-";
        const playerName = this.props.player.name || "-";
        const teamName = this.props.team.name || "-";
        const createdDate = this.toDateString(new Date(this.props.roster.createdTimestamp));
        const modifiedDate = this.toDateString(new Date(this.props.roster.modifiedTimestamp));
        return (
            <tr>
                <td>{id}</td>
                <td>{externalId}</td>
                <td>{playerName}</td>
                <td>{teamName}</td>
                <td>{createdDate}</td>
                <td>{modifiedDate}</td>
            </tr>
        );
    }
}

console.log("Exporting Roster");