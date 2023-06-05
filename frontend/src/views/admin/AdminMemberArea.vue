<template>
    <div>
       <div class="overflow-x-auto w-full">
            <table class="table w-full">
                <thead>
                <tr>
                    <th>
                    <label>
                        <input type="checkbox" class="checkbox" />
                    </label>
                    </th>
                    <th>사원명</th>
                    <th>아이디</th> 
                    <th>부서명</th>
                    <th>전화번호</th>
                    <th>이메일</th>
                    <th>입사일자</th>
                    <th>퇴사일자</th>
                    <th>사용상태</th>
                    <th>상세정보</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(member, idx) in members" id="member" :key="idx">
                    <th>
                    <label>
                        <input type="checkbox" class="checkbox" />
                    </label>
                    </th>
                    <td>
                    <div class="flex items-center space-x-3">
                        <div class="avatar">
                        <div class="mask mask-squircle w-12 h-12">
                            <img src="" alt="img" />
                        </div>
                        </div>
                        <div>
                        <div class="font-bold">{{ member.memberName }}</div>
                        </div>
                    </div>
                    </td>
                    <td>
                    {{ member.memberId }}
                    <br/>
                    </td>
                    <td>{{ member.departmentName }}</td>
                    <td>{{ member.memberCellphone }}</td>
                    <td>{{ member.memberEmail }}</td>
                    <td>{{ member.createdDate }}</td>
                    <td>{{ member.retireDate }}</td>
                    <td>{{ member.memberStatus }}</td>
                    <td>
                    <button class="btn btn-ghost btn-xs" @click="handleModal(member.memberId)">details</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div>
        <PagingView :currentPage="currentPage" :totalPages="totalPages" @selectPage="selectPage"/>
    </div>
    <AdminDetailMemberModal :isOpen="isOpen" :onToggle="onToggle" :memberId="memberId"></AdminDetailMemberModal>
</template>

<script setup>
import { ref, onMounted, watchEffect } from 'vue';
import PagingView from '../../components/pagination/pagingComponent.vue';
import AdminDetailMemberModal from './AdminDetailMemberModal.vue';
import mixins from '@/utils/mixins';

const members = ref([]);
const totalPages = ref(0);
const currentPage = ref(1);

onMounted(async () => {
    const params = {
        page: currentPage.value
    }
    const res = await mixins.methods.$api(`/admin/member`, `get`, { params });
    const arr = [];

    for (let i=0; i<res.data.content.length; i++) {
        arr.push(res.data.content[i]);
    }
    members.value = arr;

    totalPages.value = res.data.totalPages;

})

function selectPage(idx) {
    const params = {
        page: idx
    }
    const arr = [];

    watchEffect(async () => {
        const res = await mixins.methods.$api(`/admin/member`, `get`, { params });
        
        for (let i=0; i<res.data.content.length; i++) {
            arr.push(res.data.content[i]);
        }

        members.value = arr;

        totalPages.value = res.data.totalPages;
        currentPage.value = idx;
    });
}

const isOpen = ref(false);
const memberId = ref('');

const onToggle = () => {
  isOpen.value = !isOpen.value;
};

const handleModal=(id) =>{
//   if(data == '대기'){
//     uid.value = id;
//     onToggle();
//   }
    memberId.value = id;
    console.log("넘길 아이디 : " + memberId.value);
    onToggle();
}

</script>