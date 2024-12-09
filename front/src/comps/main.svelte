<!-- name, desc, desc from file, # of posts, avg likes -->
<script>
    import Segment from './segment.svelte';
    let options = ['name', 'desc', 'desc from file', '# of posts', 'avg likes'];
    let query = [
        [
            {'name':'name1','query':undefined, 'from':undefined,'to':undefined},
            {'name':'name2','query':undefined, 'from':undefined,'to':undefined},
            {'name':'name3','query':undefined, 'from':undefined,'to':undefined}
        ],
        [
            {'name':'name4','query':undefined, 'from':undefined,'to':undefined},
            {'name':'name5','query':undefined, 'from':undefined,'to':undefined},
            {'name':'name6','query':undefined, 'from':undefined,'to':undefined}
        ]
    ];

    function remove(event) {
        let { first, second } = event.detail;
        console.log(event.detail);
        query[first].splice(second, 1);
        query = query;
    }

    function addOr() {
        query.push([{'name':'name4','query':undefined, 'from':undefined,'to':undefined}]);
        query = query;
    }
</script>

<div class="column">
    {#each query as row}
    <div class="row">
            {#each row as segment}
            <Segment on:remove={remove} content={segment} last={query.indexOf(row) === 0 && row.indexOf(segment) === 0} location={`${query.indexOf(row)}${row.indexOf(segment)}`}/>
            {#if row.indexOf(segment) + 1 === row.length}
                <button>+AND</button>
            {:else if row.length > 1 && row.length < 5}
                AND
            {/if}
            {/each}
    </div>
    {/each}
    <button on:click={addOr}>+OR</button>
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