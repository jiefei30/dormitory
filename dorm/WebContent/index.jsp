<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
     <meta charset="UTF-8">
     <title>宿舍管理</title>
     <link rel="stylesheet" type="text/css" href="css/index.css">
     <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
     <script src="js/jquery.min.js"></script>
     <script src="js/bootstrap.min.js"></script>
     <script src="js/index.js?v=2017"></script>
</head>

<body>
     <div class="my-body">
          <div class="myhead">
               <div>
                    <span>学院：</span>
                    <select class="form-control" id="institute">
                         <option id="allInstitute">全部</option>
                         <option onclick="1">信息工程学院</option>
                         <option>机电学院</option>
                         <option>文法学院</option>
                         <option>教育科学学院</option>
                    </select>
               </div>
               <div>
                    <span>专业：</span>
                    <select class="form-control" id="profession">
                         <option>全部</option>
                    </select>
               </div>
               <div>
                    <span>宿舍楼：</span>
                    <select class="form-control" id="building">
                         <option>全部</option>
                         <option>1</option>
                         <option>2</option>
                    </select>
               </div>
               <div>
                    <span>房间号：</span>
                    <input type="number" id="room" class="form-control" placeholder="请输入正确的房间号" required
                         onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;">
               </div>
               <div>
                    <button type="button" id="room-search" class="btn btn-success btn-lg btn-block">查询</button>
               </div>
               <div>
                    <button type="button" id="add" class="btn btn-success btn-lg btn-block" data-toggle="modal"
                         data-target="#addModal">增加</button>
               </div>
               <div>
                    <button type="button" id="delete" class="btn btn-danger btn-lg btn-block">删除</button>
               </div>

          </div>
          <div class="fuzzy">
               <div>
                    <span>姓名：</span>
                    <input type="text" id="name" class="form-control" placeholder="请输入正确的姓名" required
                         onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;">
               </div>
               <div>
                    <button type="button" id="name-search" class="btn btn-success btn-lg">名字查询</button>
               </div>
               <div>
                    <span>学号：</span>
                    <input type="number" id="number" class="form-control" placeholder="请输入正确的学号" required
                         onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;">
               </div>
               <div>
                    <button type="button" id="number-search" class="btn btn-success btn-lg">学号查询</button>
               </div>
          </div>
     </div>
     <table border="1" class="mytable" id="mytableBody" style="margin-left:0%">
     </table>

     <div class="pageJump">
          <div><button type="button" id="firstPage"class="btn btn-success btn-lg ">首页</button></div>
          <div><button type="button" id="up" class="btn btn-success btn-lg ">上一页</button></div>
          <div><select class="form-control" id="page">
                    <option>1</option>
                    <option>2</option>
               </select></div>
          <div><button type="button" id="down" class="btn btn-success btn-lg ">下一页</button></div>
          <div><button type="button" id="lastPage" class="btn btn-success btn-lg ">末页</button></div>
     </div>


     <!-- 增加新学生模态框-->
     <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel"
          aria-hidden="true">
          <div class="modal-dialog">
               <div class="modal-content">
                    <div class="modal-header">
                         <button type="button" class="close addModalClose" data-dismiss="modal" aria-hidden="true">
                              &times;
                         </button>
                         <h4 class="modal-title" id="addModalLabel">增加</h4>
                    </div>
                    <div class="modal-body">
                         <form class="form-horizontal" role="form" id="addForm" action="javascript:;"
                              onsubmit="return addStudentInfo()" method="post">
                              <div class="form-group">
                                   <label for="addName" class="col-sm-2 control-label">姓名</label>
                                   <div class="col-sm-10">
                                        <input type="text" class="form-control" id="addName" placeholder="请输入正确的学生姓名"
                                             maxlength="5" required  title="中文"
                                             onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;">
                                   </div>
                              </div>
                              <div class="form-group">
                                   <label for="addNumber" class="col-sm-2 control-label">学号</label>
                                   <div class="col-sm-10">
                                        <input type="text" class="form-control" id="addNumber" placeholder="请输入学号"
                                             maxlength="11" pattern="^[0-9]{11,11}$" title="11个数字" required
                                             onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                                             title="警告" data-container="body" data-toggle="popover"
                                             data-placement="right" data-content="学号重复">
                                   </div>
                              </div>
                              <div class="form-group">
                                   <label for="addInstituteModal" class="col-sm-2 control-label">学院</label>
                                   <div class="col-sm-10">
                                        <select class="form-control" id="addInstituteModal" style="width:200px">
                                             <option>信息工程学院</option>
                                             <option>机电学院</option>
                                             <option>文法学院</option>
                                             <option>教育科学学院</option>
                                        </select>
                                   </div>
                              </div>
                              <div class="form-group">
                                   <label for="addProfessionModal" class="col-sm-2 control-label">专业</label>
                                   <div class="col-sm-10">
                                        <select class="form-control" id="addProfessionModal">
                                            
                                        </select>
                                   </div>
                              </div>
                              <div class="form-group">
                                   <label for="addBuildingModal" class="col-sm-2 control-label">宿舍楼</label>
                                   <div class="col-sm-10">
                                        <select class="form-control" id="addBuildingModal">
                                             <option>1</option>
                                             <option>2</option>
                                        </select>
                                   </div>
                              </div>
                              <div class="form-group">
                                   <label for="addRoomModal" class="col-sm-2 control-label">房间号</label>
                                   <div class="col-sm-10">
                                        <input type="text" class="form-control" id="addRoomModal" placeholder="请输入房间号"
                                             maxlength="3" pattern="^[0-9]{3,3}$" title="3个数字" required
                                             onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                                             title="警告" data-container="body" data-toggle="popover"
                                             data-placement="right" data-content="">
                                   </div>
                              </div>
                              <div class="modal-footer">
                                   <button type="button" class="btn btn-default addModalClose" data-dismiss="modal">关闭
                                   </button>
                                   <button type="submit" class="btn btn-primary" id="addSubmit">提交</button>
                              </div>
                         </form>
                    </div>
               </div>
          </div>
     </div>


     <!-- 修改信息模态框 -->
     <div class="modal fade" id="mdfModal" tabindex="-1" role="dialog" aria-labelledby="mdfModalLabel"
          aria-hidden="true">
          <div class="modal-dialog">
               <div class="modal-content">
                    <div class="modal-header">
                         <button type="button" class="close mdfModalClose" data-dismiss="modal" aria-hidden="true">
                              &times;
                         </button>
                         <h4 class="modal-title" id="mdfModalLabel">修改</h4>
                    </div>
                    <div class="modal-body">
                         <form class="form-horizontal" role="form" id="mdfForm" action="javascript:;"
                              onsubmit="mdfStudentInfo()">
                              <div class="form-group">
                                   <label for="mdfName" class="col-sm-2 control-label">姓名</label>
                                   <div class="col-sm-10">
                                        <input type="text" class="form-control" id="mdfName" placeholder="请输入正确的学生姓名"
                                             maxlength="5" required title="中文"
                                             onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;">
                                   </div>
                              </div>
                              <div class="form-group">
                                   <label for="mdfNumber" class="col-sm-2 control-label">学号</label>
                                   <div class="col-sm-10">
                                        <input type="text" class="form-control" id="mdfNumber" disabled>
                                   </div>
                              </div>
                              <div class="form-group">
                                   <label for="mdfInstituteModal" class="col-sm-2 control-label">学院</label>
                                   <div class="col-sm-10">
                                        <select class="form-control" id="mdfInstituteModal" style="width:200px">
                                             <option>信息工程学院</option>
                                             <option>机电学院</option>
                                             <option>文法学院</option>
                                             <option>教育科学学院</option>
                                        </select>
                                   </div>
                              </div>
                              <div class="form-group">
                                   <label for="mdfProfessionModal" class="col-sm-2 control-label">专业</label>
                                   <div class="col-sm-10">
                                        <select class="form-control" id="mdfProfessionModal">
                                             <option>通信工程</option>
                                             <option>信息工程</option>
                                        </select>
                                   </div>
                              </div>
                              <div class="form-group">
                                   <label for="mdfBuildingModal" class="col-sm-2 control-label">宿舍楼</label>
                                   <div class="col-sm-10">
                                        <select class="form-control" id="mdfBuildingModal">
                                             <option>1</option>
                                             <option>2</option>
                                        </select>
                                   </div>
                              </div>
                              <div class="form-group">
                                   <label for="mdfRoomModal" class="col-sm-2 control-label">房间号</label>
                                   <div class="col-sm-10">
                                        <input type="text" class="form-control" id="mdfRoomModal" placeholder="请输入房间号"
                                             maxlength="3" pattern="^[0-9]{3,3}$" title="3个数字" required
                                             onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                                             title="警告" data-container="body" data-toggle="popover"
                                             data-placement="right" data-content="">
                                   </div>
                              </div>
                              <div class="modal-footer">
                                   <button type="button" class="btn btn-default mdfModalClose" data-dismiss="modal">关闭
                                   </button>
                                   <button type="submit" class="btn btn-primary" id="mdfSubmit">提交</button>
                              </div>
                         </form>
                    </div>
               </div>
          </div>
     </div>
</body>
</html>