class Player extends React.Component {
    toDateString(date){
        var options = {year: 'numeric', month: '2-digit', day: '2-digit', hour: 'numeric', minute: 'numeric', second: 'numeric'};
        options.timeZone = 'UTC';
        const string = new Intl.DateTimeFormat('en', options).format(date);
        return string;
    }

    render(){
        const id = this.props.player.id || "-";
        const externalId = this.props.player.externalId || "-";
        const name = this.props.player.name || "-";
        const createdDate = this.toDateString(new Date(this.props.player.createdTimestamp));
        const modifiedDate = this.toDateString(new Date(this.props.player.modifiedTimestamp));
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

console.log("Exporting Player");