<script>
    import Row from './row.svelte';
    import GroupResult from './groupResult.svelte';
    import PostResult from './postResult.svelte';

    export let options;
    export let type;

    $: results = [];

    const textInputs = ['Name', 'Desc', 'Desc from file','Rules','Title', 'Content','File content','Comments'];
    const groupMappings = {
        'Name':'name',
        'Desc':'description',
        'Desc from file':'fileContent',
        'Rules':'rules',
        '# of posts':'numberOfPosts',
        'Avg. likes':'averageLikes'
    }

    const postMappings = {
        'Title':'title',
        'Content':'fullContent',
        'File content':'fileContent',
        'Comments':'commentContent',
        '# of likes':'numberOfLikes',
        '# of comments':'numberOfComments'
    }

    let query = [
        [
            {'name':`${type === 'group' ? 'Name' : 'Title'}`,'query':'', 'from':'','to':'','searchType':'Phraze'}
        ]
    ];

    function removeSegment(event) {
        let { queryIndex, segmentIndex } = event.detail;
        query[queryIndex].splice(segmentIndex, 1);
        if(query[queryIndex].length === 0)
            query.splice(queryIndex, 1);
        query = query;
    }

    function removeRow(event) {
        let index = parseInt(event.detail.index);
        query.splice(index, 1);
        query = query;
    }

    function addAnd(event) {
        let index = parseInt(event.detail.index);
        query[index].push({'name':'Name','query':'', 'from':'','to':'','searchType':'Phraze'});
        query = query;
    }

    function addOr() {
        query.push([{'name':'Name','query':'', 'from':'','to':'','searchType':'Phraze'}]);
        query = query;
    }

    function getMapping(key) {
        switch(type) {
            case 'group':
                return groupMappings[key];
            case 'post':
                return postMappings[key];
        }
    }

    async function search() {
        let queryString = '{\n"query": {\n\t"bool": {\n\t\t"should": [';

        for(let should of query) {
            queryString += '\n\t\t\t{'
            queryString += '\n\t\t\t\t"bool": {'
            queryString += '\n\t\t\t\t\t"must": ['

            for(let segment of should) {

                if(textInputs.indexOf(segment.name) !== -1) {
                    switch (segment.searchType) {
                        case `Phraze`:
                            queryString += `\n\t\t\t\t\t\t{ "match_phrase": { "${getMapping(segment.name)}": "${segment.query.toLowerCase()}" } }`;
                            break;
                        case 'Fuzzy':
                            queryString += `\n\t\t\t\t\t\t{ "fuzzy": { "${getMapping(segment.name)}": { "value": "${segment.query.toLowerCase()}", "fuzziness": "AUTO" } } }`;
                            break;
                    }
                } else {
                    queryString += `\n\t\t\t\t\t\t{ "range": { "${getMapping(segment.name)}": {`;

                    if(segment.from > 0)
                        queryString += ` "gte": ${segment.from}${segment.to > 0 ? "," : ""} `;

                    if(segment.to > 0)
                        queryString += ` "lte": "${segment.to}" `;

                    queryString += `} } }`;
                }


                if(should.indexOf(segment) + 1 < should.length)
                    queryString += ',';
            }
            queryString += '\n\t\t\t\t\t]'
            queryString += '\n\t\t\t\t}'
            queryString += '\n\t\t\t}'

            if(query.indexOf(should) + 1 < query.length)
                queryString += ',';
        }

        queryString += ''
        queryString += '\n\t\t]}' // close main bool
        queryString += '\n\t}' // close query

        queryString += '\n}' // close fully
        console.log(queryString);
        let formData = new FormData();
        formData.append('query', new Blob([queryString], {type: 'application/json'}));

        const response = await fetch(`http://localhost:8080/search/${type}`, {
            headers: {
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH, OPTIONS',
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': '*',
            },
            method: 'POST',
            body: formData
        });
        if (response.ok) {
            const result = await response.text();
            results = JSON.parse(result);
            console.log(result);
        } else {
            const error = await response.text();
            // alert("error");
            console.log(error);
        }
        // console.log(queryString);
    }
</script>

<div class="column">
    {#each query as row}
        <div class="column">
            <div class="row" style="gap: 10px;">
                {#if query.length > 1 && query.indexOf(row) > 0}
                    <span style="font-weight: bold;">OR </span><hr style="border-top: 5px solid #bbb; width: 100%;">
                {/if}
            </div>
            <Row row={row} on:remove={removeSegment} index={query.indexOf(row)} on:addand={addAnd} on:removerow={removeRow} options={options}></Row>
        </div>
    {/each}
    <div style="display: flex; flex-flow: row nowrap; gap: 10px; padding: 15px;">
        <button on:click={addOr}>+OR</button>
        <hr style="border-top: 5px solid #bbb; width: 100%;">
    </div>
    <button class="search" on:click={search}>Search</button>
    {#if results.length}
        <hr style="border-top: 5px solid #bbb; width: 100%;">
        {#each results as result}
            {#if type === 'group'}
                <GroupResult group={result}/>
            {:else if type === 'post'}
                <PostResult post={result}/>
            {/if}
        {/each}
    {/if}
</div>

<style>
    .row {
        display: flex;
        flex-flow: row nowrap;
    }

    .column {
        display: flex;
        flex-flow: column nowrap;
    }

    .search {
        font-size: x-large;
        width: 200px;
    }
</style>