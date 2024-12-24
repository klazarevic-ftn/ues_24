<script>
    import Search from "../../../comps/search.svelte";
    let { data } = $props(); // data.type = 'group' || 'post'

    let groupMappings = {
        'Name':'name',
        'Desc':'description',
        'Desc from file':'fileContent',
        'Rules':'rules',
        '# of posts':'numberOfPosts',
        'Avg. likes':'averageLikes'
    }

    let postMappings = {
        'Title':'title',
        'Content':'fullContent',
        'File content':'fileContent',
        'Comments':'commentContent',
        '# of likes':'numberOfLikes',
        '# of comments':'numberOfComments'
    }
</script>

<Search options={data.type === 'group' ? Object.keys(groupMappings) : Object.keys(postMappings)} type={data.type}/>
