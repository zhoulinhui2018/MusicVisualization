package com.example.demo.service.impl;

import com.example.demo.entity.Music;
import com.example.demo.dao.MusicDao;
import com.example.demo.service.MusicService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Music)表服务实现类
 *
 * @author makejava
 * @since 2020-04-12 20:55:13
 */
@Service("musicService")
public class MusicServiceImpl implements MusicService {
    @Autowired
    private MusicDao musicDao;

    @Override
    public List<Music> queryAll(int page, int limit) {
        PageHelper.startPage(page,limit);
        return musicDao.queryAll(new Music());
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Music queryById(Integer id) {
        return this.musicDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Music> queryAllByLimit(int offset, int limit) {
        return this.musicDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param music 实例对象
     * @return 实例对象
     */
    @Override
    public Music insert(Music music) {
        this.musicDao.insert(music);
        return music;
    }

    /**
     * 修改数据
     *
     * @param music 实例对象
     * @return 实例对象
     */
    @Override
    public Music update(Music music) {
        this.musicDao.update(music);
        return this.queryById(music.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.musicDao.deleteById(id) > 0;
    }
}