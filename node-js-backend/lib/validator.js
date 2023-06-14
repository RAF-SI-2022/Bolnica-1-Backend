let servPaths = {
    getReport : "getReport"
}

function getPath(path) {
    return servPaths[path];
}

function getIso(iso) {
    if (typeof iso !== 'string' || iso.length !== 3) {
      return null;
    }
    return iso === iso.toUpperCase()?iso:null;
}

function parseQuery(query) {

    let parsedParam = query.replace(/[^a-zA-Z0-9_]/g, '');
    const potentialAttacks = ["DROP TABLE", "DELETE FROM", "INSERT INTO", "UPDATE"];
    for (const attack of potentialAttacks) {
      parsedParam = parsedParam.replace(new RegExp(attack, 'gi'), '');
    }

    if(parsedParam != query) return null;
    return parsedParam;

}

module.exports = {
    getPath,
    parseQuery,
    getIso
}