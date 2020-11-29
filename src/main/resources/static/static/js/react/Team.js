class Team extends React.Component {
    toDateString(date){
        var options = {year: 'numeric', month: '2-digit', day: '2-digit', hour: 'numeric', minute: 'numeric', second: 'numeric'};
        options.timeZone = 'UTC';
        const string = new Intl.DateTimeFormat('en', options).format(date);
        return string;
    }

    render(){
        const id = this.props.team.id || "-";
        const externalId = this.props.team.externalId || "-";
        const name = this.props.team.name || "-";
        const createdDate = this.toDateString(new Date(this.props.team.createdTimestamp));
        const modifiedDate = this.toDateString(new Date(this.props.team.modifiedTimestamp));
        return (
            <tr>
                <td>{id}</td>
                <td>{externalId}</td>
                <td>{name}</td>
                <td>{createdDate}</td>
                <td>{modifiedDate}</td>
            </tr>
        );
    }
}

console.log("Exporting Team");