<!-- name, desc, desc from file, # of posts, avg likes -->
<script>
    import Row from './row.svelte';

    let query = [
        [
            {'name':'Name','query':'', 'from':'','to':''}
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
        query[index].push({'name':'Name','query':'', 'from':'','to':''});
        query = query;
    }

    function addOr() {
        query.push([{'name':'Name','query':'', 'from':'','to':''}]);
        query = query;
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
            <Row row={row} on:remove={removeSegment} index={query.indexOf(row)} on:addand={addAnd} on:removerow={removeRow}></Row>
        </div>
    {/each}
    <div style="display: flex; flex-flow: row nowrap; gap: 10px; padding: 15px;">
        <button on:click={addOr}>+OR</button>
        <hr style="border-top: 5px solid #bbb; width: 100%;">
    </div>
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
</style>