<script>
    let { data } = $props();
    let post = JSON.parse(data.post);

    async function react(event) {
        let reaction = event.target.dataset.value;
        let formData = new FormData();
        formData.append('reaction', new Blob([JSON.stringify({ group_id: data.groupId, post_id: post.id, comment_id: null, reactionType: reaction})], {type: 'application/json'}));
        try {
            const response = await fetch('http://localhost:8080/reaction', {
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
            } else {
                const error = await response.text();
                alert("error");
                console.log(error);
            }
        } catch (error) {
            console.log(error);
        }
    }

    async function deletePost() {
        try {
            const response = await fetch(`http://localhost:8080/post/${post.id}`, {
                headers: {
                    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH, OPTIONS',
                    'Access-Control-Allow-Origin': '*',
                    'Access-Control-Allow-Headers': '*',
                },
                method: 'DELETE',
            });

            if (response.ok) {
                alert('Success.');
            } else {
                const error = await response.text();
                alert('Error: ' + error);
            }
        } catch (error) {
            console.log(error);
        }

        location.href = `/group/${data.groupId}`;
    }
</script>
<a href="/group/{data.groupId}">Group</a>
<div class="main">
    <div class="row">
        <div>
            {post.title}
        </div>
        <div>
            {post.content}
        </div>
        <div class="row">
            <button on:click={react} data-value="HEART">Heart</button>
            <button on:click={react} data-value="LIKE">Like</button>
            <button on:click={react} data-value="DISLIKE">Dislike</button>
        </div>
        <button on:click={deletePost}>X</button>
    </div>
    <textarea>

    </textarea>
    <button>Comment</button>
</div>

<style>
    .main {
        display: flex;
        flex-flow: column nowrap;
        width: 33vw;
        justify-content: center;
        gap: 5px;
    }

    .row input, .row textarea {
        width: 100%;
    }
</style>