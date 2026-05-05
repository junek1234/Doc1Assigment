import { useState, useEffect } from 'react'
import './App.css'

const DEPARTMENTS = ['ACTM', 'Software Technology Engineering', 'Climate and Supply Engineering']

const emptyForm = (dept) => ({ title: '', content: '', department: dept })

export default function App() {
  const [activeTab, setActiveTab] = useState('ACTM')
  const [stories, setStories] = useState([])
  const [showForm, setShowForm] = useState(false)
  const [editingStory, setEditingStory] = useState(null)
  const [form, setForm] = useState(emptyForm('ACTM'))
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchStories()
  }, [])

  const fetchStories = async () => {
    try {
      setLoading(true)
      const res = await fetch('/api/stories')
      if (!res.ok) throw new Error('Failed to load stories')
      setStories(await res.json())
      setError(null)
    } catch (e) {
      setError('Could not connect to backend. Make sure the Spring Boot server is running on port 8080.')
    } finally {
      setLoading(false)
    }
  }

  const handleTabChange = (dept) => {
    setActiveTab(dept)
    setShowForm(false)
    setEditingStory(null)
    setForm(emptyForm(dept))
  }

  const handleAddClick = () => {
    setEditingStory(null)
    setForm(emptyForm(activeTab))
    setShowForm(true)
  }

  const handleEditClick = (story) => {
    setEditingStory(story)
    setForm({ title: story.title, content: story.content, department: story.department })
    setShowForm(true)
  }

  const handleCancel = () => {
    setShowForm(false)
    setEditingStory(null)
    setForm(emptyForm(activeTab))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      if (editingStory) {
        await fetch(`/api/stories/${editingStory.id}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(form),
        })
      } else {
        await fetch('/api/stories', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(form),
        })
      }
      handleCancel()
      fetchStories()
    } catch (e) {
      setError('Failed to save story.')
    }
  }

  const handleDelete = async (id) => {
    if (!confirm('Delete this story?')) return
    try {
      await fetch(`/api/stories/${id}`, { method: 'DELETE' })
      fetchStories()
    } catch (e) {
      setError('Failed to delete story.')
    }
  }

  const departmentStories = stories.filter((s) => s.department === activeTab)

  return (
    <div className="app">
      <header className="header">
        <div className="header-inner">
          <div className="header-date">{new Date().toDateString().toUpperCase()}</div>
          <h1 className="masthead">VIA TABLOID</h1>
          <p className="tagline">Sensational stories from VIA University College, Horsens</p>
        </div>
      </header>

      <nav className="tabs">
        {DEPARTMENTS.map((dept) => (
          <button
            key={dept}
            className={`tab-btn${activeTab === dept ? ' active' : ''}`}
            onClick={() => handleTabChange(dept)}
          >
            {dept}
          </button>
        ))}
      </nav>

      <main className="main">
        {error && <div className="error-banner">{error}</div>}

        <div className="toolbar">
          <h2 className="dept-heading">{activeTab} Department</h2>
          {!showForm && (
            <button className="btn btn-primary" onClick={handleAddClick}>
              + Add Story
            </button>
          )}
        </div>

        {showForm && (
          <form className="story-form" onSubmit={handleSubmit}>
            <h3>{editingStory ? 'Edit Story' : 'New Story'}</h3>
            <label>
              Headline
              <input
                type="text"
                placeholder="SHOCKING: Something happened at VIA..."
                value={form.title}
                onChange={(e) => setForm({ ...form, title: e.target.value })}
                required
                maxLength={200}
              />
            </label>
            <label>
              Department
              <select
                value={form.department}
                onChange={(e) => setForm({ ...form, department: e.target.value })}
              >
                {DEPARTMENTS.map((d) => (
                  <option key={d} value={d}>{d}</option>
                ))}
              </select>
            </label>
            <label>
              Story
              <textarea
                placeholder="Write the full sensational story here..."
                value={form.content}
                onChange={(e) => setForm({ ...form, content: e.target.value })}
                required
                rows={5}
              />
            </label>
            <div className="form-actions">
              <button type="submit" className="btn btn-primary">
                {editingStory ? 'Update Story' : 'Publish Story'}
              </button>
              <button type="button" className="btn btn-secondary" onClick={handleCancel}>
                Cancel
              </button>
            </div>
          </form>
        )}

        {loading ? (
          <p className="empty">Loading stories...</p>
        ) : departmentStories.length === 0 ? (
          <p className="empty">No stories yet in the {activeTab} department. Be the first!</p>
        ) : (
          <div className="stories-grid">
            {departmentStories.map((story) => (
              <article key={story.id} className="story-card">
                <h3 className="story-title">{story.title}</h3>
                <p className="story-content">{story.content}</p>
                <footer className="story-footer">
                  <span className="story-date">
                    {story.createdAt
                      ? new Date(story.createdAt).toLocaleDateString('en-GB', {
                          day: 'numeric',
                          month: 'short',
                          year: 'numeric',
                        })
                      : ''}
                  </span>
                  <div className="story-actions">
                    <button className="btn btn-secondary btn-sm" onClick={() => handleEditClick(story)}>
                      Edit
                    </button>
                    <button className="btn btn-danger btn-sm" onClick={() => handleDelete(story.id)}>
                      Delete
                    </button>
                  </div>
                </footer>
              </article>
            ))}
          </div>
        )}
      </main>
    </div>
  )
}
