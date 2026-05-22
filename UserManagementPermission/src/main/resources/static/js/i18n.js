// ==========================================
// TỪ ĐIỂN ĐA NGÔN NGỮ (i18n.js)
// ==========================================
const i18n = {
    'vn': {
        // Navigation & Layout
        'nav_home': 'Trang chủ',
        'nav_sys': 'Quản trị hệ thống',
        'nav_users': 'Quản lý Người dùng',
        'nav_groups': 'Quản lý Nhóm người dùng',
        'nav_permissions': 'Phân quyền hệ thống',
        'profile_btn': 'Hồ sơ cá nhân',
        'logout_btn': 'Đăng xuất',
        'btn_profile': 'Hồ sơ cá nhân',
        'btn_users_manage': 'Quản lý Người dùng',
        'btn_groups_manage': 'Quản lý Nhóm người dùng',
        'btn_permissions': 'Phân Quyền',
        'theme_toggle_tooltip': 'Đổi giao diện',

        // Global Headers & Actions
        'btn_add': 'Thêm Mới',
        'btn_cancel': 'Hủy',
        'btn_save': 'Lưu Thay Đổi',
        'btn_confirm': 'Xác Nhận',
        'btn_close': 'Đóng',
        'btn_save_settings': 'Lưu cài đặt',
        'th_stt': 'STT',
        'th_account': 'Tài khoản',
        'th_fullname': 'Họ và Tên',
        'th_role': 'Vai trò',
        'th_status': 'Trạng thái',
        'th_action': 'Thao tác',
        'lbl_showing': 'Đang hiển thị',

        // Home Page
        'home_overview': 'Tổng quan hệ thống',
        'home_welcome': 'Chào mừng trở lại,',
        'home_role_msg': 'Bạn đang đăng nhập với vai trò:',
        'home_instruct': 'Hãy chọn các chức năng bên dưới hoặc menu trái để bắt đầu làm việc.',
        'home_hero_sub': 'Hôm nay là một ngày tuyệt vời để quản trị hệ thống.',
        'btn_notifications': 'Thông báo',
        'msg_no_noti': 'Chưa có thông báo mới',
        'btn_settings': 'Cài đặt hệ thống',
        'modal_settings_title': 'Cài đặt Hệ thống',
        'set_anim_title': 'Hiệu ứng chuyển động (Animations)',
        'set_anim_desc': 'Tắt hiệu ứng để tăng tốc độ trang web',
        'set_sound_title': 'Âm thanh thông báo',
        'set_sound_desc': 'Phát âm thanh khi có thông báo mới',
        'set_auto_dark_title': 'Dark Mode tự động',
        'set_auto_dark_desc': 'Tự động chuyển chế độ theo hệ điều hành',
        'lbl_role': 'Vai trò của bạn',
        'lbl_login_streak': 'Chuỗi đăng nhập',
        'lbl_days_count': '12 ngày',
        'stat_total_users': 'Tổng User Hệ Thống',
        'stat_total_groups': 'Tổng số Nhóm',
        'stat_active_groups': 'Nhóm Hoạt Động',
        'stat_security': 'Chỉ số Bảo mật',
        'widget_active_tracker': 'Tần suất Hoạt động (Active)',
        'lbl_active_users_desc': 'Tài khoản đang ở trạng thái hoạt động bình thường',
        'widget_activity_map': 'Tần suất Hoạt động',
        'lbl_less': 'Ít',
        'lbl_more': 'Nhiều',
        'widget_shortcuts': 'Truy cập Nhanh',
        'widget_recent_activity': 'Hoạt động Gần đây',
        'act_login_success': 'Đăng nhập thành công',
        'act_time_just_now': 'Vừa xong',
        'act_update_profile': 'Cập nhật hồ sơ cá nhân',
        'act_time_2h': '2 giờ trước',
        'act_change_pass': 'Đổi mật khẩu bảo mật',
        'act_time_1d': '1 ngày trước',
        'act_system_update': 'Hệ thống cập nhật phiên bản v2.4.7',
        'act_time_3d': '3 ngày trước',
        'btn_view_all_act': 'Xem tất cả',
        'widget_relax_title': 'Hôm nay bạn đã làm việc vất vả rồi!',
        'widget_relax_desc': 'Hãy đứng lên vươn vai và uống một ngụm nước để giữ sức khỏe nhé. Chúc một ngày làm việc năng suất!',
        'btn_take_break': 'Nghỉ ngơi 5 phút',

        // Profile Page
        'profile_header': 'Hồ sơ cá nhân',
        'msg_profile_success': 'Cập nhật hồ sơ thành công!',
        'msg_pwd_success': 'Đã đổi mật khẩu bảo mật thành công!',
        'btn_change_avatar': 'Đổi ảnh đại diện',
        'profile_details': 'Chi tiết thông tin',
        'btn_change_pwd': 'Đổi mật khẩu',
        'btn_edit': 'Chỉnh sửa',
        'lbl_username': 'Tên tài khoản:',
        'lbl_fullname': 'Họ và tên:',
        'lbl_gender': 'Giới tính:',
        'lbl_department': 'Phòng ban:',
        'lbl_position': 'Chức vụ',
        'lbl_phone': 'Số điện thoại:',
        'lbl_email': 'Email:',
        'modal_edit_title': 'Cập nhật thông tin',
        'gender_male': 'Nam',
        'gender_female': 'Nữ',
        'gender_other': 'Khác',
        'gender_unknown': 'Chưa xác định',
        'modal_pwd_title': 'Bảo mật tài khoản',
        'lbl_old_pwd': 'Mật khẩu hiện tại',
        'ph_old_pwd': 'Nhập mật khẩu cũ',
        'lbl_new_pwd': 'Mật khẩu mới',
        'ph_new_pwd': 'Nhập mật khẩu mới',
        'lbl_confirm_pwd': 'Xác nhận mật khẩu',
        'ph_confirm_pwd': 'Nhập lại mật khẩu mới',
        'lbl_no_phone': 'Chưa cập nhật',

        // Permission Matrix Page
        'title_permission_matrix': 'Phân quyền hệ thống',
        'lbl_select_group': 'Chọn nhóm cấu hình',
        'lbl_group': 'Nhóm người dùng',
        'matrix_guide': 'Mẹo: Hệ thống sử dụng nút gạt công tắc. Gạt sang phải màu cam để kích hoạt quyền, gạt trái màu xám để tước quyền ngay lập tức.',
        'msg_select_group_first': 'Vui lòng chọn một nhóm người dùng ở bên trái để thiết lập quyền truy cập.',
        'lbl_group_permission_for': 'Quyền hạn nhóm:',
        'th_screen_name': 'Màn hình / Chức năng',
        'lbl_view': 'Quyền Xem (Menu)',
        'lbl_edit': 'Quyền Sửa (Action)',
        'btn_save_matrix': 'Lưu quyền',
        'lbl_select_group_default': '-- Chọn nhóm người dùng --',
        'screen_1': 'Quản lý người dùng',
        'screen_2': 'Quản lý nhóm người dùng',
        'screen_3': 'Phân quyền hệ thống',

        // Group Management Page
        'title_group': 'Quản lý nhóm người dùng',
        'search_group_ph': 'Tìm tên nhóm...',
        'btn_add_group': 'Thêm Nhóm',
        'th_group_name': 'Tên Nhóm',
        'th_member_count': 'Số lượng',
        'btn_manage_members': 'Quản lý thành viên',
        'msg_confirm_delete_group': 'Bạn có chắc chắn muốn xóa nhóm này? Các tài khoản thuộc nhóm sẽ mất quyền truy cập!',
        'msg_no_groups': 'Không tìm thấy dữ liệu nhóm.',
        'lbl_groups_unit': 'nhóm',
        'modal_add_group': 'Thêm Nhóm Mới',
        'modal_edit_group': 'Sửa Tên Nhóm',
        'lbl_group_name': 'Tên Nhóm',
        'lbl_users_unit_count': 'người',
        'btn_save_update': 'Lưu Cập Nhật',

        // Group Members Page
        'title_members': 'Thành viên nhóm:',
        'btn_back': 'Quay lại danh sách',
        'lbl_in_group': 'Đang trong nhóm',
        'msg_no_members': 'Nhóm này hiện chưa có thành viên nào.',
        'confirm_remove_member': 'Bạn muốn đuổi người này khỏi nhóm?',
        'btn_remove': 'Xóa',
        'lbl_available_users': 'Người dùng chưa có nhóm',
        'msg_all_users_grouped': 'Tất cả người dùng đều đã có nhóm!',
        'lbl_selected': 'Đã chọn:',
        'btn_add_to_group': 'Thêm vào nhóm',

        // User Management Page
        'search_ph': 'Tìm tên, tài khoản... (Nhấn Enter)',
        'title_user': 'Quản lý người dùng',
        'lbl_no_email': 'Chưa cập nhật email',
        'lbl_no_group': 'Chưa có',
        'status_online': 'Online',
        'status_offline': 'Offline',
        'confirm_delete_user': 'Bạn có chắc chắn muốn xóa tài khoản này không? Hành động này không thể hoàn tác!',
        'msg_no_users': 'Không tìm thấy dữ liệu.',
        'lbl_users_unit': 'người dùng',
        'title_add_user': 'Thêm người dùng mới',
        'title_edit_user': 'Cập nhật tài khoản',
        'lbl_pwd': 'Mật khẩu',
        'lbl_select_group_default_add': '-- Bỏ qua hoặc Chọn nhóm --',
        'btn_create_account': 'Tạo tài khoản',
        'th_account_status': 'Trạng thái tài khoản',
        'status_normal': 'Bình thường (Cho phép đăng nhập)',
        'status_banned': 'Đã khóa (Banned)',
        'confirm_no_group': 'Bạn chưa chọn nhóm người dùng. Tiếp tục tạo tài khoản?',
        'alert_fullname_empty': 'Họ và tên không được bỏ trống!',
        'alert_fullname_invalid': 'Họ tên sai định dạng! Không được chứa số hoặc ký tự đặc biệt.',
        'confirm_no_group_edit': 'Bạn chưa chọn Vai trò/Nhóm cho người dùng này. Vẫn tiếp tục lưu?',

        // 403 Page
        '403_title': 'Cảnh báo bảo mật',
        '403_access_denied': 'Truy cập bị từ chối',
        '403_msg': 'Tài khoản của bạn không có quyền truy cập vào chức năng này. Vui lòng liên hệ Admin nếu bạn cần được cấp quyền.',
        'btn_back_home': 'Quay lại trang chủ',

        // Login Page
        'login_title': 'ĐĂNG NHẬP',
        'login_account': 'Tài khoản',
        'login_pwd': 'Mật khẩu',
        'login_button': 'ĐĂNG NHẬP',
        'login_ph_account': 'Nhập tài khoản',
        'login_ph_pwd': 'Nhập mật khẩu'
    },
    'gb': {
        // Navigation & Layout
        'nav_home': 'Dashboard',
        'nav_sys': 'System Admin',
        'nav_users': 'User Management',
        'nav_groups': 'Group Management',
        'nav_permissions': 'System Permissions',
        'profile_btn': 'My Profile',
        'logout_btn': 'Logout',
        'btn_profile': 'My Profile',
        'btn_users_manage': 'User Management',
        'btn_groups_manage': 'Group Management',
        'btn_permissions': 'System Permissions',
        'theme_toggle_tooltip': 'Change Theme',

        // Global Headers & Actions
        'btn_add': 'Add New',
        'btn_cancel': 'Cancel',
        'btn_save': 'Save Changes',
        'btn_confirm': 'Confirm',
        'btn_close': 'Close',
        'btn_save_settings': 'Save Settings',
        'th_stt': 'No.',
        'th_account': 'Account',
        'th_fullname': 'Full Name',
        'th_role': 'Role',
        'th_status': 'Status',
        'th_action': 'Action',
        'lbl_showing': 'Showing',

        // Home Page
        'home_overview': 'System Overview',
        'home_welcome': 'Welcome back,',
        'home_role_msg': 'You are logged in as:',
        'home_instruct': 'Please select the functions below or the left menu to start working.',
        'home_hero_sub': 'Today is a great day for system administration.',
        'btn_notifications': 'Notifications',
        'msg_no_noti': 'No new notifications',
        'btn_settings': 'System Settings',
        'modal_settings_title': 'System Settings',
        'set_anim_title': 'Animations',
        'set_anim_desc': 'Disable animations to improve performance',
        'set_sound_title': 'Notification Sound',
        'set_sound_desc': 'Play sound when receiving new notifications',
        'set_auto_dark_title': 'Auto Dark Mode',
        'set_auto_dark_desc': 'Automatically switch based on OS preference',
        'lbl_role': 'Your Role',
        'lbl_login_streak': 'Login Streak',
        'lbl_days_count': '12 days',
        'stat_total_users': 'Total Users',
        'stat_total_groups': 'Total Groups',
        'stat_active_groups': 'Active Groups',
        'stat_security': 'Security Index',
        'widget_active_tracker': 'Active User Frequency',
        'lbl_active_users_desc': 'Accounts currently in normal active state',
        'widget_activity_map': 'Activity Frequency',
        'lbl_less': 'Less',
        'lbl_more': 'More',
        'widget_shortcuts': 'Quick Shortcuts',
        'widget_recent_activity': 'Recent Activity',
        'act_login_success': 'Login successful',
        'act_time_just_now': 'Just now',
        'act_update_profile': 'Profile updated',
        'act_time_2h': '2 hours ago',
        'act_change_pass': 'Password changed',
        'act_time_1d': '1 day ago',
        'act_system_update': 'System updated to v2.4.7',
        'act_time_3d': '3 days ago',
        'btn_view_all_act': 'View all',
        'widget_relax_title': 'You worked hard today!',
        'widget_relax_desc': 'Stand up, stretch, and take a sip of water to stay healthy. Have a productive day!',
        'btn_take_break': 'Take a 5-min break',

        // Profile Page
        'profile_header': 'My Profile',
        'msg_profile_success': 'Profile updated successfully!',
        'msg_pwd_success': 'Password changed successfully!',
        'btn_change_avatar': 'Change Avatar',
        'profile_details': 'Information Details',
        'btn_change_pwd': 'Change Password',
        'btn_edit': 'Edit Profile',
        'lbl_username': 'Username:',
        'lbl_fullname': 'Full Name:',
        'lbl_gender': 'Gender:',
        'lbl_department': 'Department:',
        'lbl_position': 'Position',
        'lbl_phone': 'Phone:',
        'lbl_email': 'Email:',
        'modal_edit_title': 'Update Information',
        'gender_male': 'Male',
        'gender_female': 'Female',
        'gender_other': 'Other',
        'gender_unknown': 'Unknown',
        'modal_pwd_title': 'Account Security',
        'lbl_old_pwd': 'Current Password',
        'ph_old_pwd': 'Enter current password',
        'lbl_new_pwd': 'New Password',
        'ph_new_pwd': 'Enter new password',
        'lbl_confirm_pwd': 'Confirm Password',
        'ph_confirm_pwd': 'Re-enter new password',
        'lbl_no_phone': 'Not updated',

        // Permission Matrix Page
        'title_permission_matrix': 'System Permissions',
        'lbl_select_group': 'Select Group to Configure',
        'lbl_group': 'User Group',
        'matrix_guide': 'Tip: The system uses switch toggles. Toggle orange to the right to grant permissions, toggle gray to the left to revoke them immediately.',
        'msg_select_group_first': 'Please select a user group on the left to configure access permissions.',
        'lbl_group_permission_for': 'Group Permissions:',
        'th_screen_name': 'Screen / Feature',
        'lbl_view': 'View Permission (Menu)',
        'lbl_edit': 'Edit Permission (Action)',
        'btn_save_matrix': 'Save Permissions',
        'lbl_select_group_default': '-- Select User Group --',
        'screen_1': 'User Management',
        'screen_2': 'Group Management',
        'screen_3': 'System Permissions',

        // Group Management Page
        'title_group': 'Group Management',
        'search_group_ph': 'Search group name...',
        'btn_add_group': 'Add Group',
        'th_group_name': 'Group Name',
        'th_member_count': 'Members',
        'btn_manage_members': 'Manage Members',
        'msg_confirm_delete_group': 'Are you sure you want to delete this group? Accounts in this group will lose access!',
        'msg_no_groups': 'No group data found.',
        'lbl_groups_unit': 'groups',
        'modal_add_group': 'Add New Group',
        'modal_edit_group': 'Edit Group Name',
        'lbl_group_name': 'Group Name',
        'lbl_users_unit_count': 'users',
        'btn_save_update': 'Save Update',

        // Group Members Page
        'title_members': 'Group Members:',
        'btn_back': 'Back to List',
        'lbl_in_group': 'Currently in group',
        'msg_no_members': 'This group currently has no members.',
        'confirm_remove_member': 'Do you want to remove this user from the group?',
        'btn_remove': 'Remove',
        'lbl_available_users': 'Users without Group',
        'msg_all_users_grouped': 'All users are already in a group!',
        'lbl_selected': 'Selected:',
        'btn_add_to_group': 'Add to Group',

        // User Management Page
        'search_ph': 'Search name, username...',
        'title_user': 'User Management',
        'lbl_no_email': 'Email not updated',
        'lbl_no_group': 'None',
        'status_online': 'Online',
        'status_offline': 'Offline',
        'confirm_delete_user': 'Are you sure you want to delete this account? This action cannot be undone!',
        'msg_no_users': 'No data found.',
        'lbl_users_unit': 'users',
        'title_add_user': 'Add New User',
        'title_edit_user': 'Update Account',
        'lbl_pwd': 'Password',
        'lbl_select_group_default_add': '-- Skip or Select Group --',
        'btn_create_account': 'Create Account',
        'th_account_status': 'Account Status',
        'status_normal': 'Normal (Allow login)',
        'status_banned': 'Locked (Banned)',
        'confirm_no_group': "You haven't selected a user group. Continue creating account?",
        'alert_fullname_empty': 'Full name cannot be empty!',
        'alert_fullname_invalid': 'Invalid full name format! Cannot contain numbers or special characters.',
        'confirm_no_group_edit': "You haven't selected a Group/Role for this user. Continue saving?",

        // 403 Page
        '403_title': 'Security Warning',
        '403_access_denied': 'Access Denied',
        '403_msg': 'Your account does not have permission to access this feature. Please contact the Admin if you need access.',
        'btn_back_home': 'Back to Home',

        // Login Page
        'login_title': 'LOGIN',
        'login_account': 'Account',
        'login_pwd': 'Password',
        'login_button': 'LOGIN',
        'login_ph_account': 'Enter account',
        'login_ph_pwd': 'Enter password'
    },
    'jp': {
        // Navigation & Layout
        'nav_home': 'ホーム',
        'nav_sys': 'システム管理',
        'nav_users': 'ユーザー管理',
        'nav_groups': 'グループ管理',
        'nav_permissions': 'システム権限',
        'profile_btn': 'プロフィール',
        'logout_btn': 'ログアウト',
        'btn_profile': 'プロフィール',
        'btn_users_manage': 'ユーザー管理',
        'btn_groups_manage': 'グループ管理',
        'btn_permissions': '権限管理',
        'theme_toggle_tooltip': 'テーマ変更',

        // Global Headers & Actions
        'btn_add': '新規追加',
        'btn_cancel': 'キャンセル',
        'btn_save': '変更を保存',
        'btn_confirm': '確認',
        'btn_close': '閉じる',
        'btn_save_settings': '設定を保存',
        'th_stt': '番号',
        'th_account': 'アカウント',
        'th_fullname': '氏名',
        'th_role': '役割',
        'th_status': 'ステータス',
        'th_action': '操作',
        'lbl_showing': '表示中',

        // Home Page
        'home_overview': 'システムの概要',
        'home_welcome': 'お帰りなさい、',
        'home_role_msg': '現在の役割：',
        'home_instruct': '以下の機能または左側のメニューを選択して作業を開始してください。',
        'home_hero_sub': '今日はシステム管理に最適な日です。',
        'btn_notifications': '通知',
        'msg_no_noti': '新しい通知はありません',
        'btn_settings': 'システム設定',
        'modal_settings_title': 'システム設定',
        'set_anim_title': 'アニメーション',
        'set_anim_desc': 'パフォーマンス向上のためアニメーションを無効にする',
        'set_sound_title': '通知音',
        'set_sound_desc': '新しい通知を受信したときに音を鳴らす',
        'set_auto_dark_title': '自動ダークモード',
        'set_auto_dark_desc': 'OSの設定に基づいて自動的に切り替える',
        'lbl_role': 'あなたの役割',
        'lbl_login_streak': '連続ログイン',
        'lbl_days_count': '12日',
        'stat_total_users': '総ユーザー数',
        'stat_total_groups': '総グループ数',
        'stat_active_groups': 'アクティブグループ',
        'stat_security': 'セキュリティ指数',
        'widget_active_tracker': 'アクティブユーザー頻度',
        'lbl_active_users_desc': '現在正常にアクティブなアカウント',
        'widget_activity_map': '活動頻度 (ヒートマップ)',
        'lbl_less': '少',
        'lbl_more': '多',
        'widget_shortcuts': 'クイックアクセス',
        'widget_recent_activity': '最近の活動',
        'act_login_success': 'ログイン成功',
        'act_time_just_now': 'たった今',
        'act_update_profile': 'プロフィール更新',
        'act_time_2h': '2時間前',
        'act_change_pass': 'パスワード変更',
        'act_time_1d': '1日前',
        'act_system_update': 'システムアップデート v2.4.7',
        'act_time_3d': '3日前',
        'btn_view_all_act': 'すべて見る',
        'widget_relax_title': '今日もお疲れ様です！',
        'widget_relax_desc': '立ち上がって深呼吸し、水を一口飲んでリフレッシュしましょう。生産的な一日を！',
        'btn_take_break': '5分間休憩する',

        // Profile Page
        'profile_header': 'プロフィール',
        'msg_profile_success': 'プロフィールの更新に成功しました！',
        'msg_pwd_success': 'パスワードの変更に成功しました！',
        'btn_change_avatar': 'アバターを変更',
        'profile_details': '詳細情報',
        'btn_change_pwd': 'パスワード変更',
        'btn_edit': '編集',
        'lbl_username': 'ユーザー名：',
        'lbl_fullname': '氏名：',
        'lbl_gender': '性別：',
        'lbl_department': '部署：',
        'lbl_position': '役職',
        'lbl_phone': '電話番号：',
        'lbl_email': 'メール：',
        'modal_edit_title': '情報更新',
        'gender_male': '男性',
        'gender_female': '女性',
        'gender_other': 'その他',
        'gender_unknown': '未設定',
        'modal_pwd_title': 'アカウントのセキュリティ',
        'lbl_old_pwd': '現在のパスワード',
        'ph_old_pwd': '現在のパスワードを入力',
        'lbl_new_pwd': '新しいパスワード',
        'ph_new_pwd': '新しいパスワードを入力',
        'lbl_confirm_pwd': 'パスワードの確認',
        'ph_confirm_pwd': '新しいパスワードを再入力',
        'lbl_no_phone': '未設定',

        // Permission Matrix Page
        'title_permission_matrix': 'システム権限',
        'lbl_select_group': '構成するグループを選択',
        'lbl_group': 'ユーザーグループ',
        'matrix_guide': 'ヒント: スイッチトグルを使用します。オレンジを右に切り替えると権限が付与され、左のグレーに切り替えると即座に無効になります。',
        'msg_select_group_first': 'アクセス権限を構成するには、左側でユーザーグループを選択してください。',
        'lbl_group_permission_for': 'グループの権限：',
        'th_screen_name': '画面 / 機能',
        'lbl_view': '閲覧権限 (メニュー)',
        'lbl_edit': '編集権限 (アクション)',
        'btn_save_matrix': '権限を保存',
        'lbl_select_group_default': '-- ユーザーグループを選択 --',
        'screen_1': 'ユーザー管理',
        'screen_2': 'グループ管理',
        'screen_3': 'システム権限',

        // Group Management Page
        'title_group': 'グループ管理',
        'search_group_ph': 'グループ名で検索...',
        'btn_add_group': 'グループ追加',
        'th_group_name': 'グループ名',
        'th_member_count': 'メンバー数',
        'btn_manage_members': 'メンバー管理',
        'msg_confirm_delete_group': 'このグループを削除してもよろしいですか？このグループのアカウントはアクセス権を失います！',
        'msg_no_groups': 'グループデータが見つかりません。',
        'lbl_groups_unit': 'グループ',
        'modal_add_group': '新規グループ追加',
        'modal_edit_group': 'グループ名を編集',
        'lbl_group_name': 'グループ名',
        'lbl_users_unit_count': '名',
        'btn_save_update': '更新を保存',

        // Group Members Page
        'title_members': 'グループメンバー：',
        'btn_back': '一覧に戻る',
        'lbl_in_group': '所属中',
        'msg_no_members': 'このグループには現在メンバーがいません。',
        'confirm_remove_member': 'このユーザーをグループから削除しますか？',
        'btn_remove': '削除',
        'lbl_available_users': 'グループ未所属のユーザー',
        'msg_all_users_grouped': 'すべてのユーザーがすでにグループに所属しています！',
        'lbl_selected': '選択済み：',
        'btn_add_to_group': 'グループに追加',

        // User Management Page
        'search_ph': '名前、ユーザー名を検索...',
        'title_user': 'ユーザー管理',
        'lbl_no_email': 'メール未設定',
        'lbl_no_group': 'なし',
        'status_online': 'オンライン',
        'status_offline': 'オフライン',
        'confirm_delete_user': 'このアカウントを削除してもよろしいですか？この操作は取り消せません！',
        'msg_no_users': 'データが見つかりません。',
        'lbl_users_unit': '名',
        'title_add_user': '新規ユーザー追加',
        'title_edit_user': 'アカウントの更新',
        'lbl_pwd': 'パスワード',
        'lbl_select_group_default_add': '-- スキップまたはグループを選択 --',
        'btn_create_account': 'アカウント作成',
        'th_account_status': 'アカウントステータス',
        'status_normal': '通常 (ログイン許可)',
        'status_banned': 'ロック済み (無効)',
        'confirm_no_group': 'ユーザーグループが選択されていません。アカウント作成を続行しますか？',
        'alert_fullname_empty': '氏名は必須項目です！',
        'alert_fullname_invalid': '氏名の形式が正しくありません！数字や特殊文字は使用できません。',
        'confirm_no_group_edit': 'このユーザーのグループ/役割が選択されていません。保存を続行しますか？',

        // 403 Page
        '403_title': 'セキュリティ警告',
        '403_access_denied': 'アクセス拒否',
        '403_msg': 'この機能へのアクセス権限がありません。権限が必要な場合は管理者にお問い合わせください。',
        'btn_back_home': 'ホームに戻る',

        // Login Page
        'login_title': 'ログイン',
        'login_account': 'アカウント',
        'login_pwd': 'パスワード',
        'login_button': 'ログイン',
        'login_ph_account': 'アカウントを入力',
        'login_ph_pwd': 'パスワードを入力'
    }
};

// Helper để dịch động trong code Javascript
window.getI18nText = function(key) {
    const lang = localStorage.getItem('langCode') || 'vn';
    return (i18n[lang] && i18n[lang][key]) || key;
};

// Hàm đổi ngôn ngữ (gắn vào window để gọi từ HTML)
window.changeLang = function(e, flagCode, langText) {
    if (e) e.preventDefault(); 
    
    document.getElementById('currentLangIcon').className = `fi fi-${flagCode} rounded-circle fs-5`;
    document.getElementById('currentLangText').textContent = langText;
    document.querySelectorAll('.lang-dropdown .dropdown-item').forEach(el => el.classList.remove('active-lang'));
    if (e && e.currentTarget) e.currentTarget.classList.add('active-lang');

    const dict = i18n[flagCode];
    
    // 1. Dịch text thông thường qua data-i18n
    document.querySelectorAll('[data-i18n]').forEach(element => {
        const key = element.getAttribute('data-i18n');
        if (dict[key]) {
            if (element.tagName === 'INPUT') {
                element.placeholder = dict[key];
            } else {
                const icon = element.querySelector('i');
                if (icon) {
                    element.innerHTML = '';
                    element.appendChild(icon);
                    element.append(' ' + dict[key]);
                } else {
                    element.textContent = dict[key];
                }
            }
        }
    });

    // 2. Dịch title / tooltip qua data-i18n-title
    document.querySelectorAll('[data-i18n-title]').forEach(element => {
        const key = element.getAttribute('data-i18n-title');
        if (dict[key]) {
            element.setAttribute('title', dict[key]);
        }
    });

    localStorage.setItem('langCode', flagCode);
    localStorage.setItem('langText', langText);
};

// Tự động load ngôn ngữ lúc khởi động web
document.addEventListener("DOMContentLoaded", function() {
    const savedLangCode = localStorage.getItem('langCode');
    const savedLangText = localStorage.getItem('langText');
    if (savedLangCode && savedLangText) {
        window.changeLang(null, savedLangCode, savedLangText);
    }
});