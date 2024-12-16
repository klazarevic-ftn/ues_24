
<div class="main">
    <a href="/">Home</a>
    <a href="/search/group">Search groups</a>
    <a href="/search/post">Search posts</a>
</div>

<slot/>


<style>
    .main {
        display: flex;
        flex-flow: row nowrap;
        gap: 10px;
    }
</style>