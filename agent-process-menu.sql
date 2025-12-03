-- 智能体流程功能菜单配置
-- 注意：请根据实际情况调整parent_id和sort字段的值

-- 1. 获取当前最大ID
SET @max_id = (SELECT IFNULL(MAX(id), 0) FROM flowless_menu);

-- 2. 添加AI实验室目录（如果不存在）
INSERT INTO `flowless_menu` (`id`, `parent_id`, `tree_path`, `name`, `type`, `path`, `component`, `perm`, `visible`, `sort`, `icon`, `redirect`, `create_time`, `update_time`, `del_flag`, `tenant_id`)
SELECT @max_id + 1, 0, '0', 'AI实验室', 2, '/ai-lab', 'Layout', NULL, 1, 10, 'icon-AI', '/ai-lab/agent-process', NOW(), NOW(), 0, NULL
WHERE NOT EXISTS (SELECT 1 FROM `flowless_menu` WHERE `name` = 'AI实验室');

-- 3. 获取AI实验室目录ID
SET @ai_lab_id = (SELECT `id` FROM `flowless_menu` WHERE `name` = 'AI实验室');

-- 4. 添加智能体流程菜单
INSERT INTO `flowless_menu` (`id`, `parent_id`, `tree_path`, `name`, `type`, `path`, `component`, `perm`, `visible`, `sort`, `icon`, `redirect`, `create_time`, `update_time`, `del_flag`, `tenant_id`)
SELECT @max_id + 2, @ai_lab_id, CONCAT('0,', @ai_lab_id), '智能体流程', 1, '/ai-lab/agent-process', 'ai-lab/agent-process/list', 'ai-lab:agent-process:list', 1, 1, 'icon-Process', NULL, NOW(), NOW(), 0, NULL
WHERE NOT EXISTS (SELECT 1 FROM `flowless_menu` WHERE `path` = '/ai-lab/agent-process');

-- 5. 获取智能体流程菜单ID
SET @agent_process_id = (SELECT `id` FROM `flowless_menu` WHERE `path` = '/ai-lab/agent-process');

-- 6. 添加智能体流程创建按钮权限
INSERT INTO `flowless_menu` (`id`, `parent_id`, `tree_path`, `name`, `type`, `path`, `component`, `perm`, `visible`, `sort`, `icon`, `redirect`, `create_time`, `update_time`, `del_flag`, `tenant_id`)
SELECT @max_id + 3, @agent_process_id, CONCAT('0,', @ai_lab_id, ',', @agent_process_id), '智能体流程创建', 4, NULL, NULL, 'ai-lab:agent-process:create', 1, 1, NULL, NULL, NOW(), NOW(), 0, NULL
WHERE NOT EXISTS (SELECT 1 FROM `flowless_menu` WHERE `perm` = 'ai-lab:agent-process:create');

-- 7. 添加智能体流程编辑按钮权限
INSERT INTO `flowless_menu` (`id`, `parent_id`, `tree_path`, `name`, `type`, `path`, `component`, `perm`, `visible`, `sort`, `icon`, `redirect`, `create_time`, `update_time`, `del_flag`, `tenant_id`)
SELECT @max_id + 4, @agent_process_id, CONCAT('0,', @ai_lab_id, ',', @agent_process_id), '智能体流程编辑', 4, NULL, NULL, 'ai-lab:agent-process:edit', 1, 2, NULL, NULL, NOW(), NOW(), 0, NULL
WHERE NOT EXISTS (SELECT 1 FROM `flowless_menu` WHERE `perm` = 'ai-lab:agent-process:edit');

-- 8. 添加智能体流程删除按钮权限
INSERT INTO `flowless_menu` (`id`, `parent_id`, `tree_path`, `name`, `type`, `path`, `component`, `perm`, `visible`, `sort`, `icon`, `redirect`, `create_time`, `update_time`, `del_flag`, `tenant_id`)
SELECT @max_id + 5, @agent_process_id, CONCAT('0,', @ai_lab_id, ',', @agent_process_id), '智能体流程删除', 4, NULL, NULL, 'ai-lab:agent-process:delete', 1, 3, NULL, NULL, NOW(), NOW(), 0, NULL
WHERE NOT EXISTS (SELECT 1 FROM `flowless_menu` WHERE `perm` = 'ai-lab:agent-process:delete');

-- 9. 添加智能体流程发布按钮权限
INSERT INTO `flowless_menu` (`id`, `parent_id`, `tree_path`, `name`, `type`, `path`, `component`, `perm`, `visible`, `sort`, `icon`, `redirect`, `create_time`, `update_time`, `del_flag`, `tenant_id`)
SELECT @max_id + 6, @agent_process_id, CONCAT('0,', @ai_lab_id, ',', @agent_process_id), '智能体流程发布', 4, NULL, NULL, 'ai-lab:agent-process:publish', 1, 4, NULL, NULL, NOW(), NOW(), 0, NULL
WHERE NOT EXISTS (SELECT 1 FROM `flowless_menu` WHERE `perm` = 'ai-lab:agent-process:publish');

-- 10. 添加智能体流程停用按钮权限
INSERT INTO `flowless_menu` (`id`, `parent_id`, `tree_path`, `name`, `type`, `path`, `component`, `perm`, `visible`, `sort`, `icon`, `redirect`, `create_time`, `update_time`, `del_flag`, `tenant_id`)
SELECT @max_id + 7, @agent_process_id, CONCAT('0,', @ai_lab_id, ',', @agent_process_id), '智能体流程停用', 4, NULL, NULL, 'ai-lab:agent-process:stop', 1, 5, NULL, NULL, NOW(), NOW(), 0, NULL
WHERE NOT EXISTS (SELECT 1 FROM `flowless_menu` WHERE `perm` = 'ai-lab:agent-process:stop');

-- 11. 将智能体流程菜单权限分配给admin角色（ID=2）
INSERT INTO `flowless_role_menu` (`id`, `role_id`, `menu_id`, `del_flag`, `create_time`, `update_time`)
SELECT UUID_SHORT(), '2', `id`, 0, NOW(), NOW() FROM `flowless_menu` WHERE `perm` IN (
    'ai-lab:agent-process:list',
    'ai-lab:agent-process:create',
    'ai-lab:agent-process:edit',
    'ai-lab:agent-process:delete',
    'ai-lab:agent-process:publish',
    'ai-lab:agent-process:stop'
) AND NOT EXISTS (
    SELECT 1 FROM `flowless_role_menu` WHERE `role_id` = '2' AND `menu_id` = `flowless_menu`.`id`
);

-- 12. 将智能体流程菜单权限分配给root角色（ID=1）
INSERT INTO `flowless_role_menu` (`id`, `role_id`, `menu_id`, `del_flag`, `create_time`, `update_time`)
SELECT UUID_SHORT(), '1', `id`, 0, NOW(), NOW() FROM `flowless_menu` WHERE `perm` IN (
    'ai-lab:agent-process:list',
    'ai-lab:agent-process:create',
    'ai-lab:agent-process:edit'
) AND NOT EXISTS (
    SELECT 1 FROM `flowless_role_menu` WHERE `role_id` = '1' AND `menu_id` = `flowless_menu`.`id`
);

SELECT '智能体流程菜单配置添加完成！' AS result;